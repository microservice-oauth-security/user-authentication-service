package org.codewithanish.service.impl;

import org.codewithanish.dto.AuthenticationDTO;
import org.codewithanish.entity.UserDetail;
import org.codewithanish.repository.UserDetailRepository;
import org.codewithanish.vo.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThirdPartyUserAuthenticationService extends UserAuthenticationService{

    @Autowired
    private UserDetailRepository userDetailRepository;

    public ThirdPartyUserAuthenticationService(ApiClientAuthorizationService apiClientAuthorizationService) {
        super(apiClientAuthorizationService);
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationDTO request) {
        UserDetail userDetail = userDetailRepository.findByUserNameAndProvider(request.getUserName(),
                request.getProvider()).orElseGet(() -> userDetailRepository.save(mapUserDetail(request)));
        return authenticate(userDetail);
    }
}
