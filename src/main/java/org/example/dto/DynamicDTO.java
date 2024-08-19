package org.example.dto;

import java.util.Date;

public class DynamicDTO {

    /**
     * 动态ID
     */
    private String id;

    /**
     * 动态标题
     */
    private String title;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 评论数量
     */
    private int commentCount;

    /**
     * 动态 {@link org.example.enums.DynamicStatusEnum}
     */
    private Integer status;

    /**
     *  {@link org.example.enums.DynamicTypeEnum}
     */
    private Integer type;

    /**
     * 图片数组
     */
    private String imgArray;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 删除时间
     */
    private Date deleteDate;

    /**
     * 扩展内容
     */
    private String extContent;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImgArray() {
        return imgArray;
    }

    public void setImgArray(String imgArray) {
        this.imgArray = imgArray;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getExtContent() {
        return extContent;
    }

    public void setExtContent(String extContent) {
        this.extContent = extContent;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
