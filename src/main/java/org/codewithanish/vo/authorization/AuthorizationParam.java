package org.codewithanish.vo.authorization;

import lombok.Data;

import java.util.Map;

@Data
public class AuthorizationParam {
    private Map<String, String> headerParam;
}
