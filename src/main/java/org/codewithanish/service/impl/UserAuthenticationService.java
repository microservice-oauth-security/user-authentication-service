package org.codewithanish.service.impl;

import lombok.AllArgsConstructor;
import org.codewithanish.constant.Role;
import org.codewithanish.dto.AuthenticationDTO;
import org.codewithanish.entity.UserDetail;
import org.codewithanish.service.AuthenticationService;
import org.codewithanish.vo.authorization.AuthorizationParam;
import org.codewithanish.vo.response.AuthenticationResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class UserAuthenticationService implements AuthenticationService {

    private final ApiClientAuthorizationService apiClientAuthorizationService;

    private final List<Role> userRoles =  List.of(Role.ROLE_USER);
    protected AuthenticationResponse authenticate(UserDetail userDetail)
    {
        AuthorizationParam authorizationParam = new AuthorizationParam();
        Map<String, String> headerParam = new HashMap<>();
        headerParam.put("UserName",userDetail.getUserName());
        headerParam.put("UserId", userDetail.getUserId());
        headerParam.put("UserProvider",userDetail.getProvider().toString());
        headerParam.put("UserRole",userRoles.stream().map(Enum::toString).collect(Collectors.joining(",")));
        authorizationParam.setHeaderParam(headerParam);
        return new AuthenticationResponse(apiClientAuthorizationService.generateToken(authorizationParam).accessToken());
    }

    protected UserDetail mapUserDetail(AuthenticationDTO dto)
    {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserName(dto.getUserName());
        userDetail.setProvider(dto.getProvider());
        userDetail.setUserId(generateUniqueID());
        userDetail.setCreatedTime(new Date());
        userDetail.setCreatedBy(dto.getUserName());
        return userDetail;
    }


    private String generateUniqueID() {
        return UUID.randomUUID() + "-" + Calendar.getInstance().getTimeInMillis();
    }


}
