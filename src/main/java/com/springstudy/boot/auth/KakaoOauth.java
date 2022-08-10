package com.springstudy.boot.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth{
    @Override
    public String getOAuthRedirectURL() {
        return "";
    }
}