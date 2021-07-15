package lol.gilliard.ngrok;

public enum TunnelProtocol {

    HTTP("http"), TCP("tcp"), TLS("tls");

    private final String name;

    TunnelProtocol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
