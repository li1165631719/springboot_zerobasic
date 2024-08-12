package org.example.exception;

import org.example.error.ExceptionCode;

/**
 * @Author 李志豪
 * @Date 2024/8/11 22:09
 */
public enum DynamicException implements ExceptionCode {

    DYNAMIC_TITLE_IS_NOT_NULL(2000001,"动态标题不能为空","DYNAMIC_TITLE_IS_NOT_NULL"),

    DYNAMIC_CONTENT_IS_NOT_NULL(2000002,"动态内容不能为空","DYNAMIC_CONTENT_IS_NOT_NULL"),

    DYNAMIC_TYPE_IS_NOT_EXIST(2000003,"动态类型不存在","DYNAMIC_CONTENT_IS_NOT_NULL"),





    ;

    private Integer code;

    private String message;

    private String name;

    private DynamicException(Integer code, String message, String name) {
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
