import lol.gilliard.ngrok.Ngrok;
import lol.gilliard.ngrok.NgrokClient;
import lol.gilliard.ngrok.TunnelProtocol;
import lol.gilliard.ngrok.client.TunnelDetails;
import org.junit.Assert;

public class MainTest {
    public static void main(String[] args) {
        NgrokClient client = Ngrok.startClient();
        TunnelDetails tunnel = client.connect("my tunnel", TunnelProtocol.HTTP, 8080);
        Assert.assertNotNull(((Object) tunnel).toString(), "tunnel doesn't has to be null");
        Assert.assertNotNull(tunnel.name, "tunnel.name doesn't has to be null.");
        Assert.assertNotNull(tunnel.protocol, "tunnel.protocol doesn't has to be null.");
        Assert.assertNotNull(tunnel.publicUrl, "tunnel.publicUrl doesn't has to be null.");
    }
}
