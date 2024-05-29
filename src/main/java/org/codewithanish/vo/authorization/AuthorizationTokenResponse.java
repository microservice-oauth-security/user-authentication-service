package org.codewithanish.vo.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorizationTokenResponse(@JsonProperty("access_token") String accessToken) {
}
