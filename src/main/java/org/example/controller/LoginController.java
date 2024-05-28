package org.example.controller;

import org.example.common.HttpResult;
import org.example.constant.TokenConstant;
import org.example.param.LoginParam;
import org.example.param.RegisterParam;
import org.example.result.RegisterResult;
import org.example.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 李志豪
 * @create 2024/5/28
 */
@RestController
@RequestMapping("/token")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/register")
    public HttpResult register(@RequestBody RegisterParam param, HttpServletResponse response){

        RegisterResult result =new RegisterResult();
        result.setTicket(CommonUtil.generateUUID());

        Cookie cookie =new Cookie("ticket", result.getTicket());
        cookie.setMaxAge(TokenConstant.ONE_DAY_SECONDS);
        cookie.setPath("/");
        response.addCookie(cookie);

        return HttpResult.ok();
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginParam param){

        return null;
    }
    @PostMapping("/logout")
    public String logout(){

        return null;
    }

}
