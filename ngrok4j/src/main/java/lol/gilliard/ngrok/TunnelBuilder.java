package lol.gilliard.ngrok;

import lol.gilliard.ngrok.client.TunnelDefinition;
import lol.gilliard.ngrok.client.TunnelDetails;

public class TunnelBuilder {

    private final NgrokClient ngrokClient;

    private TunnelProtocol protocol = TunnelProtocol.HTTP;
    private int port;
    private String name;

    public TunnelBuilder(NgrokClient ngrokClient) {
        this.ngrokClient = ngrokClient;
    }

    public TunnelBuilder http() {
        protocol = TunnelProtocol.HTTP;
        return this;
    }

    public TunnelBuilder name(String name){
        this.name = name;
        return this;
    }

    public TunnelBuilder port(int port){
        this.port = port;
        return this;
    }

    private TunnelDefinition buildDefinition(){
        return new TunnelDefinition(name, protocol.getName(), port);
    }

    public TunnelDetails connect() {
        return ngrokClient.connect(this.buildDefinition());
    }
}
