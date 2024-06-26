package org.example.exception;

import org.example.error.ExceptionCode;

/**
 * @author 李志豪
 * @create 2024/5/30
 */
public enum TokenException implements ExceptionCode {

    TOKEN_USERNAME_IS_NOT_NULL(100001,"用户名不能为空","TOKEN_USERNAME_IS_NOT_NULL"),
    TOKEN_USERPASSWORD_IS_NOT_NULL(100002,"用户密码不能为空","TOKEN_USERPASSWORD_IS_NOT_NULL"),
    TOKEN_USER_IS_NOT_EXISTS(100003,"用户不存在","TOKEN_USER_IS_NOT_EXISTS"),
    PASSWORD_IS_NOT_TRUE(100004,"密码不正确","PASSWORD_IS_NOT_TRUE")

    ;

    private Integer code;

    private String message;

    private String name;

    TokenException(Integer code, String message, String name) {
        this.code = code;
        this.message = message;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
