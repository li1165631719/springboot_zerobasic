package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.dto.InformDTO;
import org.example.dto.InformPageDTO;

import java.util.List;

@Mapper
public interface InformMapper {

    void addInform(InformDTO informDTO);

    Integer queryInformCount();

    List<InformPageDTO> queryCommentPage(@Param("start") Integer start,@Param("pageSize") Integer pageSize);
}
