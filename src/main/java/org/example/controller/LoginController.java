package org.example.controller;

import org.example.common.HttpResult;
import org.example.constant.TokenConstant;
import org.example.param.LoginParam;
import org.example.param.RegisterParam;
import org.example.result.RegisterResult;
import org.example.service.TokenService;
import org.example.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * cookie是保存在发送请求方的请求路径下的，不是服务端的应用路径下
 *
 * @author 李志豪
 * @create 2024/5/28
 */
@RestController
@RequestMapping("/token")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    TokenService tokenService;


    @PostMapping("/register")
    public HttpResult register(@RequestBody RegisterParam param, HttpServletResponse response){

        HttpResult httpResult=tokenService.register(param);
        if(httpResult.getData()!=null) {
            Cookie cookie = new Cookie("ticket", httpResult.getData().toString());
            cookie.setMaxAge(TokenConstant.ONE_DAY_SECONDS);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return httpResult;
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
