package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.dto.DynamicDTO;
import org.example.dto.DynamicPageDTO;

import java.util.List;

/**
 * @Author 李志豪
 * @Date 2024/8/14 21:57
 */
@Mapper
public interface DynamicMapper {

    void insertDynamic(DynamicDTO dynamicDTO);

    int queryDymamicCount();

    List<DynamicPageDTO> queryDynamicPage(@Param("start") Integer start,@Param("pageSize") Integer pageSize);

    DynamicDTO queryDynamicById(@Param("id") String dynamicId);

    void updateCommentCountById(DynamicDTO dynamicDTO);

}
