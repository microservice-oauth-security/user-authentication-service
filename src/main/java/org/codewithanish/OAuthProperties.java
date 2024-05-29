package org.codewithanish;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "oauth")
@Getter
@Setter
public class OAuthProperties {

    private String url;
    private String tokenUrl;
    private String apiClientId;
    private String apiClientSecret;
    private List<Jwt> jwts;

    @Getter
    @Setter
    public static class Jwt
    {
        private String issuerUrl;
        private String jwksUrl;
    }

}
