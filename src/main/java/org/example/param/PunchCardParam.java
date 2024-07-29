package org.example.param;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 打卡
 */
public class PunchCardParam {

     /**
     * 打卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date punchCardDate;

     /**
     * 打卡时间
     */
    private String punchCardContent;

    public Date getPunchCardDate() {
        return punchCardDate;
    }

    public void setPunchCardDate(Date punchCardDate) {
        this.punchCardDate = punchCardDate;
    }

    public String getPunchCardContent() {
        return punchCardContent;
    }

    public void setPunchCardContent(String punchCardContent) {
        this.punchCardContent = punchCardContent;
    }
}
