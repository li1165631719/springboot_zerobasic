package org.example.param;

import java.util.List;

public class PublishDynamicParam {

    /**
     * 动态标题
     */
    private String title;

    /**
     * 动态内容
     */
    private String content;

    private List<String> imgs;

    /**
     * {@link org.example.enums.DynamicTypeEnum}
     */
    private Integer type;

    /**
     * 打卡的内容
     */
    private PunchCardParam punchCardParam;

    /**
     * 投票的内容
     */
    private VoteParam voteParam;

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

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public PunchCardParam getPunchCardParam() {
        return punchCardParam;
    }

    public void setPunchCardParam(PunchCardParam punchCardParam) {
        this.punchCardParam = punchCardParam;
    }

    public VoteParam getVoteParam() {
        return voteParam;
    }

    public void setVoteParam(VoteParam voteParam) {
        this.voteParam = voteParam;
    }
}
