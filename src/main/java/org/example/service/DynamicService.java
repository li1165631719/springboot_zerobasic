package org.example.service;

import org.example.common.HttpResult;
import org.example.constant.DynamicConstant;
import org.example.dto.*;
import org.example.enums.CommentStatusEnum;
import org.example.enums.DynamicStatusEnum;
import org.example.enums.DynamicTypeEnum;
import org.example.enums.EntityTypeEnum;
import org.example.exception.CommentException;
import org.example.exception.DynamicException;
import org.example.exception.InformException;
import org.example.local.HostHolder;
import org.example.mapper.CommentMapper;
import org.example.mapper.DynamicMapper;
import org.example.mapper.InformMapper;
import org.example.mapper.VoteMapper;
import org.example.message.AsyncMessageDTO;
import org.example.message.MessageTypeEnum;
import org.example.message.dto.CommentMessageDTO;
import org.example.message.dto.TakeVoteMessageDTO;
import org.example.message.producer.MessageProducer;
import org.example.param.*;
import org.example.result.CommentPageResult;
import org.example.result.DynamicPageResult;
import org.example.result.InformPageResult;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.example.util.QiniuPictureServiceUtils;
import org.example.vo.CommentPageVO;
import org.example.vo.DynamicVO;
import org.example.vo.InformPageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 李志豪
 * @Date 2024/7/31 21:45
 */
@Service
public class DynamicService {

    private static Logger logger = LoggerFactory.getLogger(DynamicService.class);

    @Autowired
    private List<DynamicManage> dynamicManageList;//通过Autowired将DynamicManage接口通过集合的方式全部返回回来

    @Autowired
    private DynamicMapper dynamicMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private InformMapper informMapper;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private QiniuPictureServiceUtils qiniuPictureServiceUtils;

    public HttpResult publishDynamic(PublishDynamicParam param) {
        logger.info("开始发布动态，请求参数：{}", JsonUtils.objectToJson(param));

        String title = param.getTitle();
        if (StringUtils.isEmpty(title)) {
            logger.info("开始发布动态，标题不能为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_TITLE_IS_NOT_NULL);
        }

        String content = param.getContent();
        if (StringUtils.isEmpty(content)) {
            logger.info("开始发布动态，内容不能为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_CONTENT_IS_NOT_NULL);
        }
        Integer type = param.getType();
        if (DynamicTypeEnum.getDynamicTypeEnumMap(type) == null) {
            logger.info("开始发布动态，类型不存在，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_TYPE_IS_NOT_EXIST);
        }
        HttpResult result = null;
        try {
            logger.info("开始发布动态，请求参数：{}", type);
            //通过依赖注入，将spring容器实例化的对象传入dynamicManageList，
            // 再通过stream流根据type筛选出对应的功能，再调用对应的逻辑方法
            result = dynamicManageList.stream()
                    .filter(item -> type.equals(item.getType()))
                    .findFirst().get().dealDynamicPublishRequset(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public HttpResult queryDynamicPage(QueryDynamicPageParam param) {
        logger.info("分页查询动态，请求参数：{}", JsonUtils.objectToJson(param));
        Integer nowPage = param.getNowPage();
        if (ObjectUtils.isEmpty(nowPage)) {
            logger.info("分页查询动态，当前页为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_NOW_PAGE_IS_NOT_NULL);
        }
        if (nowPage < DynamicConstant.DEFAULT_NOW_PAGE_MIN_PAGE) {
            logger.info("分页查询动态，当前页小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_NOW_PAGE_IS_NOT_LESS_ONE);
        }
        Integer pageSize = param.getPageSize();
        if (ObjectUtils.isEmpty(pageSize)) {
            logger.info("分页查询动态，当前页大小为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_PAGE_SIZE_IS_NOT_NULL);
        }
        if (pageSize < DynamicConstant.DEFAULT_PAGE_SIZE_MIN_VALUE) {
            logger.info("分页查询动态，当前页大小小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_PAGE_SIZE_IS_NOT_LESS_ONE);
        }
        Integer count = dynamicMapper.queryDymamicCount();
        if (ObjectUtils.isEmpty(count) || count < DynamicConstant.DEFALUT_DYNAMIC_COUNT_MIN_VALUE) {
            logger.info("分页查询动态，当前表按照条件查询，数据量为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_COUNT_IS_NULL);
        }
        //动态图文数量/页数大小，起始页数为1
        int total = count / pageSize + 1;
        //当前页数大于总分页数
        if (nowPage > total) {
            logger.info("分页查询动态，当前页没有数据，数据量为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_NOW_PAGE_DATA_IS_NULL);
        }
        //当前起始页
        int start = (nowPage - 1) * pageSize;
        //分页查询
        List<DynamicPageDTO> list = dynamicMapper.queryDynamicPage(start, pageSize);
        //展示给用户的数据
        List<DynamicVO> voList = new ArrayList<>();
        list.forEach(item -> {
            DynamicVO dynamicVO = new DynamicVO();
            dynamicVO.setId(item.getId());//动态ID
            dynamicVO.setName(item.getName());//用户id
            dynamicVO.setTitle(item.getTitle());//动态标题
            dynamicVO.setContent(item.getContent());//动态内容
            dynamicVO.setUserId(item.getUserId());//用户ID
            dynamicVO.setCommentCount(item.getCommentCount());//评论数量
            dynamicVO.setStatus(item.getStatus());//动态
            dynamicVO.setType(item.getType());//类型
            //获取图片数组的地址，用图片的上传名称
            List<String> imgList = JsonUtils.jsonToList(item.getImgArray(), String.class);
            dynamicVO.setImgArray(new ArrayList<String>());
            for (String img : imgList
            ) {
                String fileName = img;
                String url = qiniuPictureServiceUtils.queryImage(fileName);
                dynamicVO.getImgArray().add(url);
            }
            dynamicVO.setCreatedDate(item.getCreatedDate());//创建时间
            dynamicVO.setUpdateDate(item.getUpdateDate());//更新时间
            dynamicVO.setDeleteDate(item.getDeleteDate());//删除时间
            if (!DynamicTypeEnum.BASIC.getType().equals(item.getType())) {
                HttpResult ext = dynamicManageList.stream().
                        filter(temp -> item.getType().equals(temp.getType())).
                        findFirst().get().dealDynamicExtContent(item.getExtContent());
                dynamicVO.setExt(ext.getData());
            }
            voList.add(dynamicVO);
        });

        DynamicPageResult dynamicPageResult = new DynamicPageResult();
        dynamicPageResult.setData(voList);
        dynamicPageResult.setTotal(count);
        dynamicPageResult.setDynamicStatusEnum(DynamicStatusEnum.getDynamicStatusMap());
        dynamicPageResult.setDynmaicTypeEnum(DynamicTypeEnum.getDynamicTypeMap());

        return new HttpResult(dynamicPageResult);
    }

    public HttpResult comment(CommentParam param) {
        String userID = hostHolder.getUser().getUserId();
        logger.info("开始评论, 请求参数:{}", JsonUtils.objectToJson(param));
        Integer entityType = param.getEntity_type();
        if (ObjectUtils.isEmpty(entityType)) {
            logger.info("开始评论, 评论实体类型为空, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        if (EntityTypeEnum.getEntityTypeEnumMap(entityType) == null) {
            logger.info("开始评论, 评论实体类型不正确, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        String entityId = param.getEntity_id();
        if (StringUtils.isEmpty(entityId)) {
            return HttpResult.fail();
        }
        /**
         * 验证entityID所属的实体是否存在
         */
        String dynamicId = "";
        //评论有两种情况：1、动态下的评论 2、评论下的评论  根据entityType去判断
        //1、动态下的评论
        if (EntityTypeEnum.DYNAMIC.getType().equals(entityType)) {
            DynamicDTO dynamicDTO = dynamicMapper.queryDynamicById(entityId);
            if (ObjectUtils.isEmpty(dynamicDTO)) {
                logger.info("动态查询不存在");
                return HttpResult.fail();
            }
            dynamicId = dynamicDTO.getId();
        }
        //2、评论下的评论
        if (EntityTypeEnum.COMMENT.getType().equals(entityType)) {
            CommentDTO commentDTO = commentMapper.queryCommentById(entityId);
            if (ObjectUtils.isEmpty(commentDTO)) {
                logger.info("评论查询不存在");
                return HttpResult.fail();
            }
            dynamicId = commentDTO.getId();
        }

        String content = param.getContent();
        if (StringUtils.isEmpty(content)) {
            return HttpResult.fail();
        }

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(CommonUtil.generateUUID());
        commentDTO.setEntityType(entityType);
        commentDTO.setEntityId(entityId);
        commentDTO.setContent(content);
        commentDTO.setCreateDate(new Date());
        commentDTO.setUserId(userID);
        commentDTO.setDynamicId(dynamicId);
        commentDTO.setStatus(CommentStatusEnum.NORMAL.getStatus());

        commentMapper.addComment(commentDTO);

        AsyncMessageDTO asyncMessageDTO = new AsyncMessageDTO();
        asyncMessageDTO.setId(CommonUtil.generateUUID());
        asyncMessageDTO.setUserId(userID);
        asyncMessageDTO.setType(MessageTypeEnum.COMMENT_MESSAGE.getType());
        asyncMessageDTO.setCreateDate(new Date());
        asyncMessageDTO.setMessage(commentDTO.getId());

        CommentMessageDTO commentMessageDTO = new CommentMessageDTO();
        commentMessageDTO.setId(commentDTO.getId());
        asyncMessageDTO.setMessage(JsonUtils.objectToJson(commentMessageDTO));

        logger.info("评论成功，产生异步消息需要处理，消息:{}", JsonUtils.objectToJson(asyncMessageDTO));
        messageProducer.produceMessage(JsonUtils.objectToJson(asyncMessageDTO));

        return HttpResult.ok();
    }

    public HttpResult vote(TakeVoteParam param) {
        String userID = hostHolder.getUser().getUserId();
        logger.info("开始投票,userID:{},请求参数:{}", userID, JsonUtils.objectToJson(param));
        String dynamicId = param.getDynamicId();
        if (StringUtils.isEmpty(dynamicId)) {
            return HttpResult.fail();
        }
        String voteOptionID = param.getVoteOptionId();
        if (StringUtils.isEmpty(voteOptionID)) {
            return HttpResult.fail();
        }
        DynamicDTO dynamicDTO = dynamicMapper.queryDynamicById(dynamicId);
        if (ObjectUtils.isEmpty(dynamicDTO)) {
            return HttpResult.fail();
        }
        Integer type = dynamicDTO.getType();
        if (DynamicTypeEnum.VOTE.getType().equals(type)) {
            return HttpResult.fail();
        }
        String extContent = dynamicDTO.getExtContent();
        if (StringUtils.isEmpty(extContent)) {
            return HttpResult.fail();
        }
        VoteDTO voteDTO = null;
        try {
            voteDTO = JsonUtils.jsonToPojo(extContent, VoteDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (ObjectUtils.isEmpty(voteDTO)) {
            return HttpResult.fail();
        }
        voteDTO.getOptionList().forEach(item -> {
            if (item.getId().equals(voteOptionID)) {
                item.setVoteCount(item.getVoteCount() + 1);
            }
        });
        voteDTO.setVoteTakeCount(voteDTO.getVoteTakeCount() + 1);
        /**
         * TODO 将信息更新到记录中
         * TODO 作业，1、完成投票的更新操作 2、完成消息发送，被投票的人接受到有人参与的消息
         */
        //1、完成投票的更新操作
        voteMapper.updateExt(JsonUtils.objectToJson(voteDTO), dynamicId);

        //2、完成消息发送，被投票的人接受到有人参与的消息
        AsyncMessageDTO asyncMessageDTO = new AsyncMessageDTO();
        asyncMessageDTO.setId(CommonUtil.generateUUID());
        asyncMessageDTO.setUserId(userID);
        asyncMessageDTO.setType(MessageTypeEnum.VOTE_MESSAGE.getType());
        asyncMessageDTO.setCreateDate(new Date());
        TakeVoteMessageDTO takeVoteMessageDTO = new TakeVoteMessageDTO();
        takeVoteMessageDTO.setDynamicId(param.getDynamicId());
        takeVoteMessageDTO.setVoteOptionId(param.getVoteOptionId());
        asyncMessageDTO.setMessage(JsonUtils.objectToJson(takeVoteMessageDTO));
        messageProducer.produceMessage(JsonUtils.objectToJson(asyncMessageDTO));

        return HttpResult.ok();
    }

    public HttpResult queryCommentPage(QueryCommentPageParam param) {
        logger.info("分页查询评论，请求参数：{}", JsonUtils.objectToJson(param));
        Integer nowPage = param.getNowPage();
        if (ObjectUtils.isEmpty(nowPage)) {
            logger.info("分页查询评论，当前页为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(CommentException.Comment_NOW_PAGE_IS_NOT_NULL);
        }
        if (nowPage < DynamicConstant.Comment_DEFAULT_NOW_PAGE_MIN_PAGE) {
            logger.info("分页查询评论，当前页小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(CommentException.Comment_NOW_PAGE_IS_NOT_LESS_ONE);
        }
        Integer pageSize = param.getPageSize();
        if (ObjectUtils.isEmpty(pageSize)) {
            logger.info("分页查询评论，当前页大小为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(CommentException.Comment_PAGE_SIZE_IS_NOT_NULL);
        }
        if (pageSize < DynamicConstant.Comment_DEFAULT_PAGE_SIZE_MIN_VALUE) {
            logger.info("分页查询评论，当前页大小小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(CommentException.Comment_PAGE_SIZE_IS_NOT_LESS_ONE);
        }
        Integer count = commentMapper.queryCommentCount();
        if (ObjectUtils.isEmpty(count) || count < DynamicConstant.Comment_DEFALUT_DYNAMIC_COUNT_MIN_VALUE) {
            logger.info("分页查询评论，当前表按照条件查询，数据量为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(CommentException.Comment_COUNT_IS_NULL);
        }
        //评论数量/页数大小，起始页数为1
        int total = count / pageSize + 1;
        if (nowPage > total) {
            logger.info("分页查询动态，当前页没有数据，数据量为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(CommentException.Comment_NOW_PAGE_DATA_IS_NULL);
        }
        //当前起始页
        int start = (nowPage - 1) * pageSize;
        List<CommentPageDTO> list = commentMapper.queryCommentPage(start,pageSize);

        List<CommentPageVO> voList = new ArrayList<>();

        list.forEach(item ->{
            CommentPageVO commentPageVO = new CommentPageVO();
            commentPageVO.setId(item.getId());
            commentPageVO.setUsername(item.getUsername());
            commentPageVO.setUserId(item.getUserId());
            commentPageVO.setEntityId(item.getEntityId());
            commentPageVO.setEntityType(item.getEntityType());
            commentPageVO.setContent(item.getContent());
            commentPageVO.setCreateDate(item.getCreateDate());
            commentPageVO.setDynamicId(item.getDynamicId());
            commentPageVO.setStatus(item.getStatus());
            voList.add(commentPageVO);
        });


        CommentPageResult commentPageResult = new CommentPageResult();
        commentPageResult.setTotal(count);
        commentPageResult.setData(voList);

        return new HttpResult(commentPageResult);
    }

    public HttpResult queryInformPage(QueryInformPageParam param) {
        logger.info("分页查询消息通知，请求参数：{}", JsonUtils.objectToJson(param));
        Integer nowPage = param.getNowPage();
        if (ObjectUtils.isEmpty(nowPage)) {
            logger.info("分页查询消息通知，当前页为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(InformException.Inform_NOW_PAGE_IS_NOT_NULL);
        }
        if (nowPage < DynamicConstant.Inform_DEFAULT_NOW_PAGE_MIN_PAGE) {
            logger.info("分页查询消息通知，当前页小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(InformException.Inform_NOW_PAGE_IS_NOT_LESS_ONE);
        }
        Integer pageSize = param.getPageSize();
        if (ObjectUtils.isEmpty(pageSize)) {
            logger.info("分页查询消息通知，当前页大小为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(InformException.Inform_PAGE_SIZE_IS_NOT_NULL);
        }
        if (pageSize < DynamicConstant.Inform_DEFAULT_PAGE_SIZE_MIN_VALUE) {
            logger.info("分页查询消息通知，当前页大小小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(InformException.Inform_PAGE_SIZE_IS_NOT_LESS_ONE);
        }
        Integer count = informMapper.queryInformCount();
        if (ObjectUtils.isEmpty(count) || count < DynamicConstant.Inform_DEFALUT_DYNAMIC_COUNT_MIN_VALUE) {
            logger.info("分页查询消息通知，当前表按照条件查询，数据量为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(InformException.Inform_COUNT_IS_NULL);
        }
        //消息通知数量/页数大小，起始页数为1
        int total = count / pageSize + 1;
        if (nowPage > total) {
            logger.info("分页查询消息通知，当前页没有数据，数据量为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(InformException.Inform_NOW_PAGE_DATA_IS_NULL);
        }
        //当前起始页
        int start = (nowPage - 1) * pageSize;
        List<InformPageDTO> list = informMapper.queryCommentPage(start,pageSize);
        List<InformPageVO> voList = new ArrayList<>();

        voList.forEach(item ->{
            InformPageVO informPageVO = new InformPageVO();
            informPageVO.setId(item.getId());
            informPageVO.setSendUserId(item.getSendUserId());
            informPageVO.setSendUserName(item.getSendUserName());
            informPageVO.setTakeUserId(item.getTakeUserId());
            informPageVO.setTakeUserName(item.getTakeUserName());
            informPageVO.setContent(item.getContent());
            informPageVO.setEntityId(item.getEntityId());
            informPageVO.setEntityType(item.getEntityType());

            voList.add(informPageVO);
        });
        InformPageResult informPageResult = new InformPageResult();
        informPageResult.setTotal(count);
        informPageResult.setData(voList);

        return new HttpResult(informPageResult);
    }
}
