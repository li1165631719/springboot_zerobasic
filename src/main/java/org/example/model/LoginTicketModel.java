package org.example.model;

import java.util.Date;

/**
 * @author 李志豪
 * @create 2024/5/28
 */
public class LoginTicketModel {

    private String id;

    private String userId;

    private String ticket;

    private Date expiredDate;

    /**
     *
     * {@link org.example.enums.LoginTicketStatusEnum}
     *
     * */
    private Integer status;

}
