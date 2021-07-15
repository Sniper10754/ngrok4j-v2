# ngrok4j-fixed
Original wrapper: https://github.com/mjg123/ngrok4j


<code> Unofficial Wrapper </code>

example:

```java
public class Main {
    public static void main(String[] args) {
        NgrokClient client = Ngrok.startClient();
        TunnelDetails tunnel = client.connect("my tunnel", TunnelProtocol.HTTP, 8080);
        
        System.out.println(tunnel.name);
        System.out.println(tunnel.publicUrl);
        System.out.println(tunnel.protocol);
    }
}
```