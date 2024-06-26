package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 李志豪
 * @create 2024/5/27
 */
//@Repository
@Mapper
public interface TestMapper {

    public void addTestContent(@Param("id") String id,@Param("content") String content);

    public String queryTestContentById(@Param("id") String id);
}
