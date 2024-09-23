package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.dto.CommentDTO;

@Mapper
public interface CommentMapper {
    CommentDTO queryCommentById(@Param("id") String entityId);
}
