import org.ngrok4j.Ngrok;
import org.ngrok4j.NgrokClient;
import org.ngrok4j.TunnelProtocol;
import org.ngrok4j.client.Tunnel;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // start a client
        // if NgrokClient doesn't find ngrok in path, throws a NgrokException, here you can specify the
        // Absolute path of ngrok.

        NgrokClient client = Ngrok.startClient("ngrok.exe");

        // start a tunnel
        Tunnel tunnel = client.connect("my_tunnel", TunnelProtocol.HTTP, 8080);

        // print some information
        System.out.println(tunnel.name);
        System.out.println(tunnel.publicUrl);
        System.out.println(tunnel.protocol);

        // wait until ngrok client doesn't stop

        client.getProcess().waitFor();
    }
}
