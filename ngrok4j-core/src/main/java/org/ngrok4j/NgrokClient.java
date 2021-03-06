package org.ngrok4j;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.ngrok4j.client.Tunnel;
import org.ngrok4j.client.TunnelDefinition;
import org.ngrok4j.client.TunnelList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class NgrokClient {

    private static final Logger LOGGER=LoggerFactory.getLogger(NgrokClient.class);

    public static final ObjectMapper MAPPER=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final Process process;
    private final OkHttpClient httpClient;

    private final MediaType MEDIA_TYPE_JSON=MediaType.parse("application/json; charset=utf-8");

    private final String baseUrl;

    protected NgrokClient(Process ngrokProcess, String ngrokWebServiceUrl) {
        this.process=ngrokProcess;
        this.baseUrl="http://" + ngrokWebServiceUrl + "/api";
        httpClient=new OkHttpClient();
    }

    /**
     * returns a tunnel
     *
     * @param definition a TunnelDefinition to build the tunnel
     * @return Tunnel
     */
    public Tunnel connect(TunnelDefinition definition) {
        try {
            String body=MAPPER.writeValueAsString(definition);

            Request request=new Request.Builder().url(baseUrl + "/tunnels").method("POST", RequestBody.create(body, MEDIA_TYPE_JSON)).build();

            Response response=httpClient.newCall(request).execute();


            return MAPPER.readValue(response.body().string(), Tunnel.class);

        } catch (IOException e) {
            throw new NgrokException("Failed to create tunnel", e);
        }
    }

    /**
     * Raw constructor of Class TunnelDefinition
     *
     * @param name     Name of the tunnel
     * @param protocol Tunnel protocol
     * @param port     Tunnel port
     * @return Tunnel
     */
    public Tunnel connect(String name, TunnelProtocol protocol, int port) {
        TunnelDefinition definition=new TunnelDefinition(name, protocol.getName(), port);
        return connect(definition);
    }

    /**
     * Returns a list of the active tunnels.
     *
     * @return List of active tunnels
     */

    public List<Tunnel> listTunnels() {

        try {
            Request request=new Request.Builder().url(baseUrl + "/tunnels").header("accept", "application/json").build();

            Response response=httpClient.newCall(request).execute();

            return MAPPER.readValue(response.body().string(), TunnelList.class).tunnels;

        } catch (IOException e) {
            throw new NgrokException("Failed to list tunnels", e);
        }

    }

    /**
     * Returns a tunnel from the name.
     *
     * @param tunnelName TunnelName
     * @return Tunnel
     */
    public Tunnel getTunnel(String tunnelName) {
        try {
            Request request=new Request.Builder().url(baseUrl + "/tunnels/" + tunnelName).header("accept", "application/json").build();

            Response response=httpClient.newCall(request).execute();

            if (! response.isSuccessful()) {
                return null;
            }

            return MAPPER.readValue(response.body().string(), Tunnel.class);

        } catch (IOException e) {
            throw new NgrokException("Failed to list tunnels", e);
        }
    }

    /**
     * Disconnects a Tunnel from name.
     *
     * @param tunnelName Tunnel name
     */
    public void disconnect(String tunnelName) {

        try {
            Request request=new Request.Builder().url(baseUrl + "/tunnels/" + tunnelName).delete().build();

            Response response=httpClient.newCall(request).execute();

            if (! response.isSuccessful()) {
                throw new NgrokException(String.format("Failed to disconnect tunnel: %s", response.body().string()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shuts down all tunnels
     */
    public void disconnectAll() {
        listTunnels().forEach(tunnel -> disconnect(tunnel.name));
    }

    /**
     * Shutdowns the client process.
     */
    public void shutdown() {
        try {
            process.destroy(); // polite request
            process.waitFor(); // blocks until the process is gone

        } catch (InterruptedException e) {
            throw new NgrokException("Interrupted while waiting for ngrok to shut down", e);
        }
    }

    /**
     *
     * @return TunnelBuilder
     */
    public TunnelBuilder build() {
        return new TunnelBuilder(this);
    }

    /**
     * Returns the ngrok process
     * be careful and don't touch streams, and other things that can
     * damage ngrok.
     * @return Ngrok process
     */
    public Process getProcess() {
        return process;
    }
}
