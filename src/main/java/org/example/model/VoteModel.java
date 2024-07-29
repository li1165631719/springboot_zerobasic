package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class VoteModel {

    private String id;

    /**
     * 动态Id
     */
    private String dynamicId;

    /**
     * 投票标题
     */
    private String voteTitle;

    /**
     * 参与投票的人数
     */
    private Integer voteTakeCount;

    /**
     * 选项集合
     */
    private List<VoteOptionModel> optionList;
}
