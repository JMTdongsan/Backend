package com.matdongsan.demo.configs.securityconfig.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2UserDTO {

    private String role;
    private String name;
    private String username;
}
