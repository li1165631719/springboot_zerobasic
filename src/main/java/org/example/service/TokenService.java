package org.example.service;

import org.apache.commons.lang3.StringUtils;
import org.example.common.HttpResult;
import org.example.constant.TokenConstant;
import org.example.dto.LoginTicketDTO;
import org.example.dto.UserDTO;
import org.example.enums.LoginTicketStatusEnum;
import org.example.exception.TokenException;
import org.example.mapper.LoginTicketMapper;
import org.example.mapper.UserMapper;
import org.example.param.RegisterParam;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @author 李志豪
 * @create 2024/5/30
 */
@Service
public class TokenService {

    Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginTicketMapper loginTicketMapper;

    
    @Transactional(value = "Exception")
    public HttpResult register(RegisterParam param) {
        logger.info("开始注册，请求参数：{}", JsonUtils.objectToJson(param));
        String name = param.getName();
        if (StringUtils.isEmpty(name)) {
            logger.info("开始注册，用户名为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.TOKEN_USERNAME_IS_NOT_NULL);
        }
        String password = param.getPassword();
        if (StringUtils.isEmpty(password)) {
            logger.info("开始注册，用户密码为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.TOKEN_USERNAME_IS_NOT_NULL);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(CommonUtil.generateUUID());
        userDTO.setName(name);
        String salt = CommonUtil.generateUUID().substring(0, 6);
        userDTO.setSalt(salt);
        userDTO.setPassword(CommonUtil.MD5(password+salt));
        try{
            userMapper.addUserDTO(userDTO);
            logger.info("开始注册，用户数据添加成功，请求参数：{}，userDTO：{}", JsonUtils.objectToJson(param),JsonUtils.objectToJson(userDTO));
            LoginTicketDTO loginTicketDTO =new LoginTicketDTO();
            loginTicketDTO.setId(CommonUtil.generateUUID());
            loginTicketDTO.setUserId(userDTO.getId());
            loginTicketDTO.setTicket(CommonUtil.generateUUID());
            Long time = new Date().getTime() + TokenConstant.DEFAULT_TICKET_EXPIRED_TIME;
            loginTicketDTO.setExpiredDate(new Date(time));
            loginTicketDTO.setStatus(LoginTicketStatusEnum.NORMAL.getStatus());
            loginTicketMapper.addLoginTicketDTO(loginTicketDTO);
            logger.info("开始注册，用户令牌添加成功，请求参数：{}，loginTicketDTO：{}", JsonUtils.objectToJson(param),JsonUtils.objectToJson(loginTicketDTO));
            return new HttpResult(loginTicketDTO.getTicket());
        }catch (Exception e){
            logger.error("开始注册，发生异常，请求参数：{}",JsonUtils.objectToJson(param));
            e.printStackTrace();
            return HttpResult.fail();
        }

    }
}
