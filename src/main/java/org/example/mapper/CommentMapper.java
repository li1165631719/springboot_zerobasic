package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.dto.CommentDTO;
import org.example.dto.CommentPageDTO;
import org.example.vo.CommentPageVO;

import java.util.List;

@Mapper
public interface CommentMapper {
    CommentDTO queryCommentById(@Param("id") String entityId);

    void addComment(CommentDTO commentDTO);

    Integer queryCommentCount();

    List<CommentPageDTO> queryCommentPage(@Param("start") Integer start, @Param("pageSize")Integer pageSize);
}
