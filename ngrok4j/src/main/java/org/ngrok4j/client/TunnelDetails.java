package org.ngrok4j.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TunnelDetails {

    public final String publicUrl;
    public final String protocol;
    public final String name;

    public TunnelDetails(
        @JsonProperty("public_url") String publicUrl,
        @JsonProperty("proto") String protocol,
        @JsonProperty("name") String name
    ) {
        this.publicUrl = publicUrl;
        this.protocol = protocol;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TunnelDetails{" +
               "publicUrl='" + publicUrl + '\'' +
               ", protocol='" + protocol + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
