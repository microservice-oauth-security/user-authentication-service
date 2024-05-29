package org.codewithanish.service.impl;

import org.codewithanish.dto.AuthenticationDTO;
import org.codewithanish.entity.UserDetail;
import org.codewithanish.exception.UserAlreadyExistsException;
import org.codewithanish.repository.UserDetailRepository;
import org.codewithanish.vo.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SignInUserAuthenticationService extends UserAuthenticationService{

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SignInUserAuthenticationService(ApiClientAuthorizationService apiClientAuthorizationService) {
        super(apiClientAuthorizationService);
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationDTO request) {
        if(userDetailRepository.findByUserNameAndProvider(request.getUserName(), request.getProvider()).isPresent())
        {
            throw new UserAlreadyExistsException();
        }else {
          UserDetail userDetail = mapUserDetail(request);
          userDetail.setPassword(passwordEncoder.encode(request.getPassword()));
          userDetail =  userDetailRepository.save(userDetail);
          return authenticate(userDetail);
        }
    }
}
