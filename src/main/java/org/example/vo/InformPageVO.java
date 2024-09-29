package org.example.vo;

/**
 * 消息通知实体
 */
public class InformPageVO {

    //消息通知实体id
    private String id;

    //消息发送者id
    private String sendUserId;

    //消息发送者姓名
    private String sendUserName;

    //消息回复者id
    private String takeUserId;

    //消息回复者姓名
    private String takeUserName;


    //消息发送内容
    private String content;

    //被回复消息主体id
    private String entityId;

    //被回复消息主题类型
    private Integer entityType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getTakeUserId() {
        return takeUserId;
    }

    public void setTakeUserId(String takeUserId) {
        this.takeUserId = takeUserId;
    }

    public String getTakeUserName() {
        return takeUserName;
    }

    public void setTakeUserName(String takeUserName) {
        this.takeUserName = takeUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }
}
