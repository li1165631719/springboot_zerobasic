package org.example.controller;

import com.mysql.cj.log.LogFactory;
import org.example.param.LoginParam;
import org.example.param.RegisterParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * @author 李志豪
 * @create 2024/5/28
 */
@Controller
@RequestMapping("/token")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/register")
    public String register(@RequestBody RegisterParam param){

        return null;
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
