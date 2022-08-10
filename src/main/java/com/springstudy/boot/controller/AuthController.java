package com.springstudy.boot.controller;

import com.springstudy.boot.auth.AuthTokenProvider;
import com.springstudy.boot.auth.Constant;
import com.springstudy.boot.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final OAuthService oAuthService;
    private final AuthTokenProvider authTokenProvider;

    private String redirectURL;





//    @GetMapping("/auth/login/{socialLoginType}")
//    public String socialLoginRedirect(@PathVariable(name = "socialLoginType") String SocialLoginPath) throws IOException{
//        Constant.SocialLoginType socialLoginType = Constant.SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
//        redirectURL = oAuthService.request(socialLoginType);
//
//        return String.format("redirect:%s", redirectURL);
//    }
//
//    @ResponseBody
//    @GetMapping("/auth/{socialLoginType}/callback")
//    public void poo(){}



}
