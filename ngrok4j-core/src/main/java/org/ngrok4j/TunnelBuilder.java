package org.ngrok4j;

import org.ngrok4j.client.TunnelDefinition;
import org.ngrok4j.client.Tunnel;

public class TunnelBuilder {

    private final NgrokClient ngrokClient;

    private TunnelProtocol protocol = TunnelProtocol.HTTP;
    private int port;
    private String name;

    public TunnelBuilder(NgrokClient ngrokClient) {
        this.ngrokClient = ngrokClient;
    }

    /**
     * sets the protocol to HTTP.
     * @return TunnelBuilder
     */
    public TunnelBuilder http() {
        protocol = TunnelProtocol.HTTP;
        return this;
    }

    /**
     * Sets the tunnel name
     * @param name Tunnel name
     * @return TunnelBuilder
     */
    public TunnelBuilder name(String name){
        this.name = name;
        return this;
    }

    /**
     * Sets the tunnel port
     * @param port Tunnel port
     * @return TunnelBuilder
     */
    public TunnelBuilder port(int port){
        this.port = port;
        return this;
    }

    /**
     * Builds the TunnelDefinition
     * @return TunnelDefinition
     */
    private TunnelDefinition buildDefinition(){
        return new TunnelDefinition(name, protocol.getName(), port);
    }

    /**
     * Builds the TunnelDefinition
     *
     * @return Tunnel
     */
    public Tunnel connect() {
        return ngrokClient.connect(this.buildDefinition());
    }
}
