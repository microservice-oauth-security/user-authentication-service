package org.codewithanish.service;

import org.codewithanish.vo.authorization.AuthorizationParam;
import org.codewithanish.vo.authorization.AuthorizationTokenResponse;

public interface AuthorizationService {
       AuthorizationTokenResponse generateToken(AuthorizationParam param);

}
