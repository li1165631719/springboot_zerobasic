package org.example.service;

import org.apache.commons.lang3.StringUtils;
import org.example.common.HttpResult;
import org.example.constant.TokenConstant;
import org.example.dto.LoginTicketDTO;
import org.example.dto.UserDTO;
import org.example.enums.LoginTicketStatusEnum;
import org.example.exception.TokenException;
import org.example.local.HostHolder;
import org.example.mapper.LoginTicketMapper;
import org.example.mapper.UserMapper;
import org.example.param.LoginParam;
import org.example.param.RegisterParam;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.example.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;


/**
 * @author 李志豪
 * @create 2024/5/30
 */
@Service
public class TokenService {

    private static Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private HostHolder hostHolder;


    @Transactional(rollbackFor = Exception.class)
    public HttpResult register(RegisterParam param) {
        logger.info("开始注册，请求参数：{}", JsonUtils.objectToJson(param));
        String name = param.getName();
        //参数的校验
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
        userDTO.setUserId(CommonUtil.generateUUID());
        userDTO.setName(name);
        String salt = CommonUtil.generateUUID().substring(0, 6);
        userDTO.setSalt(salt);
        userDTO.setPassword(CommonUtil.MD5(password + salt));
        try {
            userMapper.addUserDTO(userDTO);
            logger.info("开始注册，用户数据添加成功，请求参数：{}，userDTO：{}", JsonUtils.objectToJson(param), JsonUtils.objectToJson(userDTO));
            LoginTicketDTO loginTicketDTO = new LoginTicketDTO();
            loginTicketDTO.setId(CommonUtil.generateUUID());
            loginTicketDTO.setUserId(userDTO.getUserId());
            loginTicketDTO.setTicket(CommonUtil.generateUUID());
            Long time = new Date().getTime() + TokenConstant.DEFAULT_TICKET_EXPIRED_TIME;
            loginTicketDTO.setExpiredDate(new Date(time));
            loginTicketDTO.setStatus(LoginTicketStatusEnum.NORMAL.getStatus());
            loginTicketMapper.addLoginTicketDTO(loginTicketDTO);
            logger.info("开始注册，用户令牌添加成功，请求参数：{}，loginTicketDTO：{}", JsonUtils.objectToJson(param), JsonUtils.objectToJson(loginTicketDTO));

            return new HttpResult(loginTicketDTO.getTicket());
        } catch (Exception e) {
            logger.error("开始注册，发生异常，请求参数：{}", JsonUtils.objectToJson(param));
            e.printStackTrace();
            throw e;
        }

    }

    public HttpResult login(LoginParam param) {
        try {
            logger.info("开始登录，请求参数：{}", JsonUtils.objectToJson(param));
            //参数的校验
            String name = param.getName();
            if (StringUtils.isEmpty(name)) {
                logger.info("开始登录，用户名为空，请求参数：{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(TokenException.TOKEN_USERNAME_IS_NOT_NULL);
            }
            String password = param.getPassword();
            if (StringUtils.isEmpty(password)) {
                logger.info("开始登录，用户密码为空，请求参数：{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(TokenException.TOKEN_USERNAME_IS_NOT_NULL);
            }
            UserDTO userDTO = userMapper.queryUserDTO(name);

            if (ObjectUtils.isEmpty(userDTO)) {
                logger.info("开始登录，用户不存在，请求参数：{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(TokenException.TOKEN_USER_IS_NOT_EXISTS);
            }

            if (!userDTO.getPassword().equals(CommonUtil.MD5(password + userDTO.getSalt()))) {
                logger.info("开始登录，密码不正确，请求参数：{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(TokenException.PASSWORD_IS_NOT_TRUE);
            }
            logger.info("开始登录，用户名密码正确，开始更新令牌，请求参数：{}", JsonUtils.objectToJson(param));
            //获取当前用户登录状态
            String userId = userDTO.getUserId();
            LoginTicketDTO loginTicketDTO = loginTicketMapper.queryTicketDTO(userId);
            //对当前用户状态进行更新（当前时间+3天，并将用户状态置为有效）
            Long time = new Date().getTime() + TokenConstant.DEFAULT_TICKET_EXPIRED_TIME;
            loginTicketDTO.setExpiredDate(new Date(time));
            loginTicketDTO.setStatus(LoginTicketStatusEnum.NORMAL.getStatus());
            loginTicketMapper.updateTicketDTO(loginTicketDTO);
            //返回用户token
            return new HttpResult(loginTicketDTO.getTicket());
        } catch (Exception e) {
            logger.info("开始登录，发生异常，请求参数：{}", JsonUtils.objectToJson(param));
            e.printStackTrace();
            throw e;
        }
    }

    public HttpResult logout() {
        UserVO userVO = hostHolder.getUser();
        logger.info("开始退出，用户信息：{}", JsonUtils.objectToJson(userVO));
        UserDTO userDTO = userMapper.queryUserDTO(userVO.getName());
        String userId = userDTO.getUserId();
        LoginTicketDTO loginTicketDTO = loginTicketMapper.queryTicketDTO(userId);
        loginTicketDTO.setStatus(LoginTicketStatusEnum.EXPIRED.getStatus());
        loginTicketMapper.updateTicketDTO(loginTicketDTO);
        return HttpResult.ok();
    }
}
