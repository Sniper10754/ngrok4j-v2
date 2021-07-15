import lol.gilliard.ngrok.Ngrok;
import lol.gilliard.ngrok.NgrokClient;
import lol.gilliard.ngrok.TunnelProtocol;
import lol.gilliard.ngrok.client.TunnelDetails;

public class Main {
    public static void main(String[] args) {
        NgrokClient client = Ngrok.startClient();
        TunnelDetails tunnel = client.connect("my tunnel", TunnelProtocol.HTTP, 8080);

        System.out.println(tunnel.name);
        System.out.println(tunnel.publicUrl);
        System.out.println(tunnel.protocol);

        // wait until ngrok client doesn't stop
        try {
            client.getProcess().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
