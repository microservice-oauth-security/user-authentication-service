package org.codewithanish.service.impl;

import org.codewithanish.OAuthProperties;
import org.codewithanish.service.AuthorizationService;
import org.codewithanish.vo.authorization.AuthorizationParam;
import org.codewithanish.vo.authorization.AuthorizationTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ApiClientAuthorizationService implements AuthorizationService {

    @Autowired
    private OAuthProperties oAuthProperties;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AuthorizationTokenResponse generateToken(AuthorizationParam param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(basicEncodeCredential(oAuthProperties.getApiClientId(),oAuthProperties.getApiClientSecret()));
        param.getHeaderParam().forEach(headers::set);
        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);
        return restTemplate.postForObject(oAuthProperties.getTokenUrl(), request, AuthorizationTokenResponse.class);
    }

    private String basicEncodeCredential(String id, String secret)
    {
        return Base64.getEncoder().encodeToString((id+":"+secret).getBytes(StandardCharsets.UTF_8));
    }
}
