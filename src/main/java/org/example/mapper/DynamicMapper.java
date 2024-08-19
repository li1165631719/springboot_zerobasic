package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.dto.DynamicDTO;

/**
 * @Author 李志豪
 * @Date 2024/8/14 21:57
 */
@Mapper
public interface DynamicMapper {

    void insertDynamic(DynamicDTO dynamicDTO);



}
