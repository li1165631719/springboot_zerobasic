package org.example.controller;

import org.checkerframework.checker.units.qual.A;
import org.example.common.HttpResult;
import org.example.message.MessageConstant;
import org.example.param.*;
import org.example.service.DynamicService;
import org.example.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李志豪
 * @Date 2024/7/29 22:38
 */

@RestController
@RequestMapping("dynamic")
public class DynamicController {


    @Autowired
    private DynamicService service;

    @PostMapping("/publish")
    public HttpResult publishDynamic(@RequestBody PublishDynamicParam param){
        return service.publishDynamic(param);
    }

    @PostMapping("/query/page")
    public HttpResult queryDynamicPage(@RequestBody QueryDynamicPageParam param){
        return service.queryDynamicPage(param);
    }

    @PostMapping("/comment")
    public HttpResult comment(@RequestBody CommentParam param){
        return service.comment(param);
    }

    @PostMapping("/vote")
    public HttpResult vote(@RequestBody TakeVoteParam param){
        return service.vote(param);
    }

    @PostMapping("/query/comment")
    public HttpResult queryCommentPage(@RequestBody QueryCommentPageParam param){
        return service.queryCommentPage(param);
    }

    @PostMapping("/query/inform")
    public HttpResult queryInformPage(@RequestBody QueryInformPageParam param){
        return service.queryInformPage(param);
    }
    /**
     * TODO 1、评论分页查询  1
     * TODO 2、消息通知     1
     * TODO 3、消息分页
     * TODO 4、个人主页展示------个人信息、个人动态、点赞或者收藏的动态
     * TODO 5、敏感词的过滤------前缀树算法
     * TODO 6、删除动态/评论
     * TODO 7、添加动态的话题
     * TODO 8、通过redis搞一个动态热度排行榜，热度规则：点赞的权重+评论的权重+收藏的权重
     * TODO 9、给用户一个积分，做签到，积分兑换商品；
     */



}
