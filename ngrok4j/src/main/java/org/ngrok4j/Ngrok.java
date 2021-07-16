package org.ngrok4j;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ngrok {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ngrok.class);

    private static final Pattern STARTUP_WEB_SERVICE_URL =
        Pattern.compile(".*starting web service.*addr=(\\d+\\.\\d+\\.\\d+\\.\\d+:\\d+).*");

    private static final Pattern SESSION_ESTABLISHED = Pattern.compile(".*client session established.*");

    private static final Pattern NGROK_VERSION = Pattern.compile("ngrok version (.+)");

    /**
     * Returns the Ngrok executable name
     * in base on the OS.
     * @return String
     */
    private static String defaultNgrokBinaryName(){
        return System.getProperty("os.name").startsWith("Windows") ? "ngrok.exe" : "ngrok";
    }

    /**
     * returns the version of the Ngrok executable
     * @return String
     */
    public static String getVersion() {
        return getVersion(defaultNgrokBinaryName());
    }

    /**
     * @param binaryName the Ngrok binary name.
     * @return Binary ngrok version
     */
    public static String getVersion(String binaryName) {
        try {
            Process ngrokProcess = new ProcessBuilder(binaryName, "--version")
                .redirectErrorStream(true)
                .start();

            BufferedReader ngrokStdout = new BufferedReader(new InputStreamReader(ngrokProcess.getInputStream()));
            String line;

            while ((line = ngrokStdout.readLine()) != null) {

                LOGGER.debug(line);

                Matcher matcher = NGROK_VERSION.matcher(line);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }

            throw new NgrokException("Did not find version string in ngrok output");

        } catch (IOException e) {
            throw new NgrokException("Failed to start ngrok process", e);
        }

    }

    /**
     * start a client
     * @return NgrokClient
     */
    public static NgrokClient startClient() {
        return startClient(defaultNgrokBinaryName());
    }

    /**
     * starts a Client from a specified location
     * @param binaryName Ngrok binary name
     * @return Specific NgrokClient
     */
    public static NgrokClient startClient(String binaryName) {

        try {

            Process ngrokProcess = new ProcessBuilder(binaryName, "start", "--none", "-log=stdout")
                .redirectErrorStream(true)
                .start();

            String ngrokWebServiceUrl =
                    CompletableFuture
                    .supplyAsync(() -> waitForWebServiceUrl(ngrokProcess))
                    .get(5, TimeUnit.SECONDS)
                        .replace("4041", "4040");

            if (ngrokWebServiceUrl == null) {
                ngrokProcess.destroy();
                throw new NgrokException("Didn't find local web service URL from ngrok");
            }

            return new NgrokClient(ngrokProcess, ngrokWebServiceUrl);

        } catch (Exception e) {
            throw new NgrokException("Failed to start ngrok process", e);
        }

    }

    @Nullable
    private static String waitForWebServiceUrl(Process ngrokProcess) {
        // ngrok startup isn't instant. Even after the process starts it's not ready to use immediately.
        // Read ngrok's stdout waiting for a couple of important messages
        BufferedReader ngrokStdout = new BufferedReader(new InputStreamReader(ngrokProcess.getInputStream()));

        String ngrokWebServiceUrl = null;

        String line;

        try {
            while ((line = ngrokStdout.readLine()) != null) {

                LOGGER.debug(line);

                Matcher matcher = STARTUP_WEB_SERVICE_URL.matcher(line);
                if (matcher.find()) {
                    ngrokWebServiceUrl = matcher.group(1);
                }

                if (SESSION_ESTABLISHED.matcher(line).find()) {
                    break;
                }

                // TODO: this is an infinite loop on failed startup
            }
            return ngrokWebServiceUrl;

        } catch (IOException e){
            return null;
        }
    }
}
