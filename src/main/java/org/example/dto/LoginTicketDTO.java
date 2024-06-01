package org.example.dto;

import java.util.Date;

/**
 * @author 李志豪
 * @create 2024/5/30
 */
public class LoginTicketDTO {

    private String id;

    private String userId;

    private String ticket;

    private Date expiredDate;

    /**
     *
     * {@link org.example.enums.LoginTicketStatusEnum}
     *
     * 定义类的时候多用包装类，为什么不用int，因为会赋予初始值，如果赋予一个0，这会对业务造成影响
     * 而用包装类初始化的时候就是一个null不会对业务造成影响
     *
     * */
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
