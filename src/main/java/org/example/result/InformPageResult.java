package org.example.result;

import org.example.vo.CommentPageVO;
import org.example.vo.InformPageVO;

import java.util.List;

/**
 * @Author 李志豪
 * @Date 2024/8/26 23:32
 */
public class InformPageResult {

    private Integer total;

    private List<InformPageVO> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<InformPageVO> getData() {
        return data;
    }

    public void setData(List<InformPageVO> data) {
        this.data = data;
    }
}
