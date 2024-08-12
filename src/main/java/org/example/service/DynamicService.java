package org.example.service;

import org.example.common.HttpResult;
import org.example.enums.DynamicTypeEnum;
import org.example.exception.DynamicException;
import org.example.param.PublishDynamicParam;
import org.example.util.JsonUtils;
import org.mockito.internal.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author 李志豪
 * @Date 2024/7/31 21:45
 */
@Service
public class DynamicService {

    private static  Logger logger = LoggerFactory.getLogger(DynamicService.class);

    @Autowired
    private List<DynamicManage> dynamicManageList;//通过Autowired将DynamicManage接口通过集合的方式全部返回回来

    public HttpResult publishDynamic(PublishDynamicParam param){
        logger.info("开始发布动态，请求参数：{}", JsonUtils.objectToJson(param));

        String title = param.getTitle();
        if(StringUtils.isEmpty(title)){
            logger.info("开始发布动态，标题不能为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_TITLE_IS_NOT_NULL);
        }

        String content = param.getContent();
        if(StringUtils.isEmpty(content)){
            logger.info("开始发布动态，内容不能为空，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_CONTENT_IS_NOT_NULL);
        }
        Integer type = param.getType();
        if(DynamicTypeEnum.getDynamicTypeEnumMap(type)==null){
            logger.info("开始发布动态，类型不存在，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_TYPE_IS_NOT_EXIST);
        }


        try {

            logger.info("开始发布动态，请求参数：{}", type);

            //通过依赖注入，将spring容器实例化的对象传入dynamicManageList，
            // 再通过stream流根据type筛选出对应的功能，再调用对应的逻辑方法
            dynamicManageList.stream()
                    .filter(item->type.equals(item.getType()))
                    .findFirst().get().dealDynamicPublishRequset(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;
    }


}
