package org.example.vo;

import java.util.Date;

public class PunchCardVO {

    private  String id;

    /**
     * 动态ID
     */
    private String dynamicId;

    /**
     * 打卡时间
     */
    private Date punCardDate;

    /**
     * 打卡用语
     */
    private String punchCardContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public Date getPunCardDate() {
        return punCardDate;
    }

    public void setPunCardDate(Date punCardDate) {
        this.punCardDate = punCardDate;
    }

    public String getPunchCardContent() {
        return punchCardContent;
    }

    public void setPunchCardContent(String punchCardContent) {
        this.punchCardContent = punchCardContent;
    }
}
