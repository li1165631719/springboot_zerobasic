package org.example.exception;

import org.example.error.ExceptionCode;

public enum CommentException implements ExceptionCode {
    Comment_NOW_PAGE_IS_NOT_NULL(3000001,"当前页不能为空","Comment_NOW_PAGE_IS_NOT_NULL"),

    Comment_NOW_PAGE_IS_NOT_LESS_ONE(3000002,"当前页不能小于1","Comment_NOW_PAGE_IS_NOT_LESS_ONE"),

    Comment_PAGE_SIZE_IS_NOT_NULL(3000003,"当前页大小不能为空","Comment_PAGE_SIZE_IS_NOT_NULL"),

    Comment_PAGE_SIZE_IS_NOT_LESS_ONE(3000004,"评论页大小不能小于1","Comment_PAGE_SIZE_IS_NOT_LESS_ONE"),

    Comment_COUNT_IS_NULL(3000005,"当前条件查询数量为0","Comment_COUNT_IS_NULL"),

    Comment_NOW_PAGE_DATA_IS_NULL(3000006,"当前页没有数据","Comment_NOW_PAGE_DATA_IS_NULL"),

    ;

    private Integer code;

    private String message;

    private String name;

    private CommentException(Integer code, String message, String name) {
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
