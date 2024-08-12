package org.example.model;

import java.util.Date;

public class DynamicModel {

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
    private String commentCount;

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
     * 删除时间
     */
    private Date deletdDate;

    /**
     * 扩展内容
     */
    private String extContent;
}
