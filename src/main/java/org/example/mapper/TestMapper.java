package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 李志豪
 * @create 2024/5/27
 */
@Mapper
public interface TestMapper {

    public void addTestContent(@Param("id") String id,@Param("content") String content);

    public void queryTestContentById(@Param("id") String id);
}
