package org.example.param;

/**
 * @Author 李志豪
 * @Date 2024/8/19 21:25
 */
public class QueryDynamicPageParam {

    private Integer nowPage;

    private Integer pageSize;

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
