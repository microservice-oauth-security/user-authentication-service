package org.codewithanish.service;

import org.codewithanish.dto.AuthenticationDTO;
import org.codewithanish.vo.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationDTO request);

}
