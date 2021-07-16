package org.ngrok4j;

public enum TunnelProtocol {

    HTTP("http"), TCP("tcp"), TLS("tls");

    private final String name;

    TunnelProtocol(String name) {
        this.name = name;
    }

    /**
     * @return Protocol name
     */
    public String getName() {
        return name;
    }
}
