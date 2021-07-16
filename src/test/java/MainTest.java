import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ngrok4j.Ngrok;
import org.ngrok4j.NgrokClient;

public class MainTest {
    NgrokClient client;

    @BeforeEach
    public void init() {
        client = Ngrok.startClient(".\\src\\test\\resources\\ngrok.exe");
    }

    @AfterEach
    public void deinit() {
        client.shutdown();
    }

    @Test
    public void testParameters() {
        Assertions.assertNotNull(client, "Client doesn't has to be null.");
    }
}
