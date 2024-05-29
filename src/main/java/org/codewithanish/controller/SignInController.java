package org.codewithanish.controller;

import org.codewithanish.constant.Provider;
import org.codewithanish.dto.AuthenticationDTO;
import org.codewithanish.service.impl.SignInUserAuthenticationService;
import org.codewithanish.vo.request.AuthenticationRequest;
import org.codewithanish.vo.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.codewithanish.controller.ControllerConstant.END_POINT;

@RestController
@RequestMapping(END_POINT+"/sign-in")
public class SignInController {

    @Autowired
    private SignInUserAuthenticationService signInUserAuthenticationService;


    @PostMapping
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest request)
    {
      return new ResponseEntity<>(signInUserAuthenticationService.authenticate(AuthenticationDTO.builder()
                .userName(request.userName())
                .password(request.password())
                .provider(Provider.LOCAL).build()), HttpStatus.OK);

    }


}
