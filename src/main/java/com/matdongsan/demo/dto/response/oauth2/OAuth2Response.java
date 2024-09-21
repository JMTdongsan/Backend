package com.matdongsan.demo.dto.response.oauth2;

public interface OAuth2Response {

    /*
    getProvider: 제공자 (Naver, Google 등)
    getProviderId: 제공자가 발급해주는 아이디
    getEmail: 사용자 이메일
    getName: 사용자가 설정한 이름
     */
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
