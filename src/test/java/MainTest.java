import org.ngrok4j.Ngrok;
import org.ngrok4j.NgrokClient;
import org.ngrok4j.TunnelProtocol;
import org.ngrok4j.client.TunnelDetails;
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
