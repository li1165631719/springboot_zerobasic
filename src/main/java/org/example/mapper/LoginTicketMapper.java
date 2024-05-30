package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.dto.LoginTicketDTO;

/**
 * @author 李志豪
 * @create 2024/5/30
 */
@Mapper
public interface LoginTicketMapper {

    public void addLoginTicketDTO(LoginTicketDTO loginTicketDTO);
}
