package org.ngrok4j;

import org.ngrok4j.client.Tunnel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class NgrokTest {

    private NgrokClient client;

    @BeforeEach
    public void clientInit() {
        client = Ngrok.startClient(".\\src\\test\\resources\\ngrok.exe");
    }

    @AfterEach
    public void clientShutdown() {
        client.shutdown();
    }

    @Test
    public void createAndFetchTunnelDetails() {
        Tunnel tunnel = client.connect("test-tunnel", TunnelProtocol.HTTP, 8080);
    }

    @Test
    public void instantiateTwoNgroks() {
        // should be fine, no exceptions
        NgrokClient client2 = Ngrok.startClient(".\\src\\test\\resources\\ngrok.exe");
        client2.shutdown();
    }

    @Test
    public void doubleShutdownHasNoEffect() {
        client.shutdown();
        // second shutdown in @AfterEach. No exception.
    }

    @Test
    public void listTunnels() {
        client.connect("test-tunnel", TunnelProtocol.HTTP, 8080);

        List<Tunnel> allTunnels = client.listTunnels();

        // 2 because you get an "http" and an "https"
        assertEquals(2, allTunnels.size());

        List<String> protocols = allTunnels.stream()
            .map(td -> td.protocol)
            .collect(Collectors.toList());

        assertTrue(protocols.contains("http"));
        assertTrue(protocols.contains("https"));
    }

    @Test
    public void fetchTunnelByName() {
        client.connect("test-tunnel", TunnelProtocol.HTTP, 8080);
        Tunnel tunnel = client.getTunnel("test-tunnel");

        assertEquals("test-tunnel", tunnel.name);
        assertEquals("https", tunnel.protocol);
    }

    @Test
    public void fetchTunnelThatDoesntExist() {
        Tunnel tunnel = client.getTunnel("doesn't exist");
        assertNull(tunnel);
    }

    @Test
    public void tunnelBuilder(){

        String tunnelName = "builder pattern tunnel";

        client.build()
            .http()
            .port(8080)
            .name(tunnelName)
            .connect();

        assertEquals(2, client.listTunnels().size());

        Tunnel tunnel = client.getTunnel(tunnelName);
        assertEquals(tunnelName, tunnel.name);
    }

    @Test
    public void shutDownTunnel(){
        String tunnelName = "will be shut down";

        client.connect(tunnelName, TunnelProtocol.HTTP, 8080);
        assertEquals(2, client.listTunnels().size());

        // createTunnel calls ngrok which will actually create *2* tunnels
        // so we need to delete them both.
        client.disconnect(tunnelName);
        client.disconnect(tunnelName + " (http)");
        assertEquals(0, client.listTunnels().size());
    }

    @Test
    public void shutDownAllTunnels(){
        String tunnelName = "will be shut down";

        client.connect(tunnelName, TunnelProtocol.HTTP, 8080);
        assertEquals(2, client.listTunnels().size());

        client.disconnectAll();
        assertEquals(0, client.listTunnels().size());
    }

    @Test
    public void shutDownTunnelWhichDoesNotExist(){
        String tunnelName = "does not exist";
        assertThrows(NgrokException.class, () -> client.disconnect(tunnelName));
    }

    @Test
    public void connectAfterShutdownShouldFailWithException(){
        client.shutdown();

        assertThrows(NgrokException.class, () ->
            client.connect("name", TunnelProtocol.HTTP, 8080));
    }


}
