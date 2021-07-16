# ngrok4j-v2
Original wrapper: https://github.com/mjg123/ngrok4j


<code> Unofficial Wrapper </code>

example: <code>./gradlew run</code>

```java
public class Main {
    public static void main(String[] args) {
        // start a client
        // if NgrokClient doesn't find ngrok in path, throws a NgrokException, here you can specify the
        // Absolute path of ngrok.

        NgrokClient client = Ngrok.startClient("C:\\Users\\Sniper10754\\OneDrive\\Desktop\\ngrok.exe");

        // start a tunnel

        TunnelDetails tunnel = client.connect("my tunnel", TunnelProtocol.HTTP, 8080);

        // print some information
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
```
