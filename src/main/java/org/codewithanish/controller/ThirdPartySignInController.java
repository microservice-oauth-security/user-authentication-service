package org.codewithanish.controller;

import org.codewithanish.constant.Provider;
import org.codewithanish.dto.AuthenticationDTO;
import org.codewithanish.service.impl.ThirdPartyUserAuthenticationService;
import org.codewithanish.vo.request.ThirdPartyAuthenticationRequest;
import org.codewithanish.vo.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.codewithanish.controller.ControllerConstant.END_POINT;

@RestController
@RequestMapping(END_POINT+"/3p/sign-in")
public class ThirdPartySignInController {

    @Autowired
    private ThirdPartyUserAuthenticationService thirdPartyUserAuthenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> signIn(Authentication auth, @RequestBody ThirdPartyAuthenticationRequest request)
    {
        return new ResponseEntity<>(thirdPartyUserAuthenticationService.authenticate(AuthenticationDTO.builder()
                        .userName(getUserName(auth, request.provider()))
                .provider(request.provider()).build()), HttpStatus.OK);

    }

    private String getUserName(Authentication auth, Provider provider) {
        String userName = null;
        Jwt jwt = (Jwt) auth.getPrincipal();
        if (provider.equals(Provider.GOOGLE)) {
            userName = jwt.getClaimAsString("email");
        }
        return userName;
    }

}
