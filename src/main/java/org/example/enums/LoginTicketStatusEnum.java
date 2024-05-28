package org.example.enums;

/**
 * @author 李志豪
 * @create 2024/5/28
 */
public enum LoginTicketStatusEnum {
    NORMAL(0,"有效","NORMAL"),

    EXPIRED(1,"无效","EXPIRED")
    ;

    private Integer status;

    private String desc;

    private String name;

    LoginTicketStatusEnum(Integer status, String desc, String name) {
        this.status = status;
        this.desc = desc;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }
}
