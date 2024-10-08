package org.example.enums;

import java.util.HashMap;
import java.util.Map;

public enum CommentStatusEnum {
    NORMAL(1, "正常", "NORMAL"),

    DEL(2, "删除", "DEL")

    ;

    private Integer status ;

    private String desc;

    private String name;

    CommentStatusEnum(Integer status, String desc, String name) {
        this.status = status;
        this.desc = desc;
        this.name = name;
    }

    private static Map<Integer,String> dynamicStatusMap = new HashMap<>();

    static {
        for(CommentStatusEnum temp:CommentStatusEnum.values()){
            dynamicStatusMap.put(temp.getStatus(),temp.getDesc());
        }
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
