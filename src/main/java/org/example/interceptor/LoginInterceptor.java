package org.example.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.example.common.HttpResult;
import org.example.dto.LoginTicketDTO;
import org.example.dto.UserDTO;
import org.example.enums.LoginTicketStatusEnum;
import org.example.error.CommonExceptionEnum;
import org.example.local.HostHolder;
import org.example.mapper.LoginTicketMapper;
import org.example.mapper.UserMapper;
import org.example.util.JsonUtils;
import org.example.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author 李志豪
 * @Date 2024/6/2 4:21
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入拦截请求，uri:{}", request.getRequestURI());
        Cookie[] cookies = request.getCookies();
        String ticket = null;
        for (Cookie cookie : cookies
        ) {
            if (cookie.getName().equals("ticket")) {
                ticket = cookie.getValue();
            }
        }
        if(StringUtils.isEmpty(ticket)){
            logger.info("进入拦截请求，uri:{}", request.getRequestURI());
            response.setContentType("application/json;charset=UTF-8");
            HttpResult result =HttpResult.generateHttpResult(CommonExceptionEnum.TOKEN_NOT_NULL);
            response.getWriter().println(JsonUtils.objectToJson(result));
            return false;
        }

        LoginTicketDTO loginTicketDTO =loginTicketMapper.queryTicketDTOByTicket(ticket);

        if(ObjectUtils.isEmpty(loginTicketDTO)||
                loginTicketDTO.getStatus().equals(LoginTicketStatusEnum.EXPIRED)||
                        loginTicketDTO.getExpiredDate().getTime() < new Date().getTime()){
            response.setContentType("application/json;charset=UTF-8");
            HttpResult result =HttpResult.generateHttpResult(CommonExceptionEnum.TOKEN_NOT_NULL);
            response.getWriter().println(JsonUtils.objectToJson(result));
            return false;
        }
        String userId = loginTicketDTO.getUserId();
        UserDTO userDTO = userMapper.queryUserDTOByUserId(userId);

        UserVO userVO =new UserVO();
        userVO.setUserId(userDTO.getUserId());
        userVO.setName(userDTO.getName());

        hostHolder.setUser(userVO);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
