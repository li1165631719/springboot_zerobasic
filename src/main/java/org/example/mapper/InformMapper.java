package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.dto.InformDTO;

@Mapper
public interface InformMapper {

    void addInform(InformDTO informDTO);
}
