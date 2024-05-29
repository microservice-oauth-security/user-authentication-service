package org.codewithanish.service.impl;

import org.codewithanish.dto.AuthenticationDTO;
import org.codewithanish.entity.UserDetail;
import org.codewithanish.exception.AuthenticationException;
import org.codewithanish.exception.UserNotFoundException;
import org.codewithanish.repository.UserDetailRepository;
import org.codewithanish.vo.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUserAuthenticationService extends UserAuthenticationService{

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginUserAuthenticationService(ApiClientAuthorizationService apiClientAuthorizationService) {
        super(apiClientAuthorizationService);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationDTO request) {
        UserDetail userDetail = userDetailRepository.findByUserNameAndProvider(request.getUserName(), request.getProvider()).orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(request.getPassword(), userDetail.getPassword()))
        {
            throw new AuthenticationException();
        }
        return authenticate(userDetail);
    }
}
