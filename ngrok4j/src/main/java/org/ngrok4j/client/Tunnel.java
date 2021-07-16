package org.ngrok4j.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tunnel {

    public final String publicUrl;
    public final String protocol;
    public final String name;

    public Tunnel(
        @JsonProperty("public_url") String publicUrl,
        @JsonProperty("proto") String protocol,
        @JsonProperty("name") String name
    ) {
        this.publicUrl = publicUrl;
        this.protocol = protocol;
        this.name = name;
    }

    /**
     * Builds a String definition of the tunnel
     * @return String definition
     */
    @Override
    public String toString() {
        return "Tunnel{" +
               "publicUrl='" + publicUrl + '\'' +
               ", protocol='" + protocol + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
