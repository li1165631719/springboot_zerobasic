package org.example.param;

import java.util.ArrayList;
import java.util.List;

/**
 * 投票
 */
public class VoteParam {

    /**
     * 投票标题
     */
    private String voteTitle;

    private List<VoteOptionParam> optionList =new ArrayList<>();

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public List<VoteOptionParam> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<VoteOptionParam> optionList) {
        this.optionList = optionList;
    }
}
