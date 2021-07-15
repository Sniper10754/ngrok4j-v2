package lol.gilliard.ngrok.client;

public class TunnelDefinition {

    public final String name;
    public final String proto;
    public final int addr;

    public TunnelDefinition(String name, String proto, int addr) {
        this.name = name;
        this.proto = proto;
        this.addr = addr;
    }

    public TunnelDefinition(String proto, int addr) {
        this.name = null;
        this.proto = proto;
        this.addr = addr;
    }
}
