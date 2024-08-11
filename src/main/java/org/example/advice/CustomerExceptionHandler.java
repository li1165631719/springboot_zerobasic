package org.example.advice;

import org.example.common.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 拦截异常并进行通知
 *
 * @Author 李志豪
 * @Date 2024/6/1 16:55
 */
@RestControllerAdvice
public class CustomerExceptionHandler {

    final static Logger logger = LoggerFactory.getLogger(CustomerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public HttpResult exceptionHandler(Exception e){

        logger.info("捕获到未知异常");

        e.printStackTrace();

        return HttpResult.fail();
    }
}
