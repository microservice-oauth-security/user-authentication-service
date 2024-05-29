package org.codewithanish.dto;

import lombok.Builder;
import lombok.Data;
import org.codewithanish.constant.Provider;

@Data
@Builder
public class AuthenticationDTO {

    private String userName;
    private String password;
    private Provider provider;
}
