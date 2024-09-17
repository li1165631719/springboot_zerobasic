package org.example;

import org.example.Application;
import org.example.common.HttpResult;
import org.example.message.MessageConstant;
import org.example.param.RegisterParam;
import org.example.service.TokenService;
import org.example.util.CommonUtil;
import org.example.util.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 李志豪
 * @create 2024/5/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringbootTest1 {


    @Autowired
    TokenService tokenService;

    @Test
    public void testSalt(){
        String salt = CommonUtil.generateUUID().substring(0, 6);
        System.out.println(salt);
    }

    @Test
    public void testTokenService(){
        RegisterParam registerParam =new RegisterParam();
        registerParam.setName("user-1");
        registerParam.setPassword("123456");
        tokenService.register(registerParam);
    }

    @Autowired
    JedisUtil jedisUtil;

    @Test
    public void testJedis(){
        jedisUtil.set("springboot-basic","100");
        System.out.println("第一次获取到key的值："+jedisUtil.get("springboot-basic"));
        jedisUtil.incr("springboot-basic");
        System.out.println("第二次获取到key的值："+jedisUtil.get("springboot-basic"));
        jedisUtil.incrBy("springboot-basic",10L);
        System.out.println("第三次获取到key的值："+jedisUtil.get("springboot-basic"));
    }


}
