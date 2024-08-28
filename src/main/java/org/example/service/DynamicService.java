package org.example.service;

import net.minidev.json.JSONUtil;
import org.example.common.HttpResult;
import org.example.constant.DynamicConstant;
import org.example.dto.DynamicDTO;
import org.example.dto.DynamicPageDTO;
import org.example.enums.DynamicStatusEnum;
import org.example.enums.DynamicTypeEnum;
import org.example.exception.DynamicException;
import org.example.mapper.DynamicMapper;
import org.example.param.PublishDynamicParam;
import org.example.param.QueryDynamicPageParam;
import org.example.result.DynamicPageResult;
import org.example.util.JsonUtils;
import org.example.util.QiniuPictureServiceUtils;
import org.example.vo.DynamicVO;
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
        try {

            logger.info("开始发布动态，请求参数：{}", type);

            //通过依赖注入，将spring容器实例化的对象传入dynamicManageList，
            // 再通过stream流根据type筛选出对应的功能，再调用对应的逻辑方法
            dynamicManageList.stream()
                    .filter(item -> type.equals(item.getType()))
                    .findFirst().get().dealDynamicPublishRequset(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
}
