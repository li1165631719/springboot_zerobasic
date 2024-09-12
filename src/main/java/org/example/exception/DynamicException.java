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

    DYNAMIC_NOW_PAGE_IS_NOT_NULL(2000004,"当前页不能为空","DYNAMIC_NOW_PAGE_IS_NOT_NULL"),

    DYNAMIC_NOW_PAGE_IS_NOT_LESS_ONE(2000005,"当前页不能小于1","DYNAMIC_NOW_PAGE_IS_NOT_LESS_ONE"),

    DYNAMIC_PAGE_SIZE_IS_NOT_NULL(2000006,"当前页大小不能为空","DYNAMIC_PAGE_SIZE_IS_NOT_NULL"),

    DYNAMIC_PAGE_SIZE_IS_NOT_LESS_ONE(2000007,"动态页大小不能小于1","DYNAMIC_PAGE_SIZE_IS_NOT_LESS_ONE"),

    DYNAMIC_COUNT_IS_NULL(2000008,"当前条件查询数量为0","DYNAMIC_COUNT_IS_NULL"),

    DYNAMIC_NOW_PAGE_DATA_IS_NULL(2000009,"当前页没有数据","DYNAMIC_NOW_PAGE_DATA_IS_NULL"),

    DYNAMIC_VOTE_TITLE_IS_NULL(2000010,"投票标题为空","DYNAMIC_VOTE_TITLE_IS_NULL"),

    DYNAMIC_VOTE_OPTIONLIST_IS_NULL(2000011,"投票选项为空或投票选项个数小于1","DYNAMIC_VOTE_OPTIONLIST_IS_NULL"),
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
