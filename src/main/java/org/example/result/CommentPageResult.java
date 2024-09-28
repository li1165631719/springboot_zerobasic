package org.example.result;

import org.example.vo.CommentPageVO;
import org.example.vo.DynamicVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 李志豪
 * @Date 2024/8/26 23:32
 */
public class CommentPageResult {

    private Integer total;

    private List<CommentPageVO> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<CommentPageVO> getData() {
        return data;
    }

    public void setData(List<CommentPageVO> data) {
        this.data = data;
    }
}
