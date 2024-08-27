package org.example.result;

import org.example.vo.DynamicVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 李志豪
 * @Date 2024/8/26 23:32
 */
public class DynamicPageResult {

    private Integer total;

    private List<DynamicVO> data;

    private Map<Integer,String> dynamicStatusEnum = new HashMap<>();

    private Map<Integer,String> dynmaicTypeEnum = new HashMap<>();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DynamicVO> getData() {
        return data;
    }

    public void setData(List<DynamicVO> data) {
        this.data = data;
    }

    public Map<Integer, String> getDynamicStatusEnum() {
        return dynamicStatusEnum;
    }

    public void setDynamicStatusEnum(Map<Integer, String> dynamicStatusEnum) {
        this.dynamicStatusEnum = dynamicStatusEnum;
    }

    public Map<Integer, String> getDynmaicTypeEnum() {
        return dynmaicTypeEnum;
    }

    public void setDynmaicTypeEnum(Map<Integer, String> dynmaicTypeEnum) {
        this.dynmaicTypeEnum = dynmaicTypeEnum;
    }
}
