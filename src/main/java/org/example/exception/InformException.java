package org.example.exception;

import org.example.error.ExceptionCode;

public enum InformException implements ExceptionCode {
    Inform_NOW_PAGE_IS_NOT_NULL(4000001,"当前页不能为空","Inform_NOW_PAGE_IS_NOT_NULL"),

    Inform_NOW_PAGE_IS_NOT_LESS_ONE(4000002,"当前页不能小于1","Inform_NOW_PAGE_IS_NOT_LESS_ONE"),

    Inform_PAGE_SIZE_IS_NOT_NULL(4000003,"当前页大小不能为空","Inform_PAGE_SIZE_IS_NOT_NULL"),

    Inform_PAGE_SIZE_IS_NOT_LESS_ONE(4000004,"评论页大小不能小于1","Inform_PAGE_SIZE_IS_NOT_LESS_ONE"),

    Inform_COUNT_IS_NULL(4000005,"当前条件查询数量为0","Inform_COUNT_IS_NULL"),

    Inform_NOW_PAGE_DATA_IS_NULL(4000006,"当前页没有数据","Inform_NOW_PAGE_DATA_IS_NULL"),

    ;

    private Integer code;

    private String message;

    private String name;

    private InformException(Integer code, String message, String name) {
        this.code = code;
        this.message = message;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
