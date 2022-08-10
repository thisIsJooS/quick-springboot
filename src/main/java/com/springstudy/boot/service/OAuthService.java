package com.springstudy.boot.service;

import com.springstudy.boot.auth.Constant;
import com.springstudy.boot.auth.GoogleOauth;
import com.springstudy.boot.auth.KakaoOauth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final GoogleOauth googleOauth;
    private final KakaoOauth kakaoOauth;

    public String request(Constant.SocialLoginType socialLoginType) throws IOException{
        String redirectURL;
        switch (socialLoginType){
            case GOOGLE:{
                // 각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스
                redirectURL = googleOauth.getOAuthRedirectURL();
            }break;
            case KAKAO:{
                redirectURL = kakaoOauth.getOAuthRedirectURL();
            }
            default:{
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }

        return redirectURL;
    }
}
