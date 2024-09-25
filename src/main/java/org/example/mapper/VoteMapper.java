package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.dto.VoteDTO;

@Mapper
public interface VoteMapper {
    void updateExt(@Param("extContent") String voteDTO,@Param("dynamicId") String dynamicId);


}
