package org.example.dto;

/**
 * 消息通知实体
 */
public class InformDTO {

    //消息通知实体id
    private String id;

    //消息发送者id
    private String sendUserId;

    //消息回复者id
    private String takeUserId;

    //消息回复内容
    private String content;

    //被回复消息主体id
    private String entityId;

    //被回复消息主题类型
    private String entityType;

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

    public String getTakeUserId() {
        return takeUserId;
    }

    public void setTakeUserId(String takeUserId) {
        this.takeUserId = takeUserId;
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

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
