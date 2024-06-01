package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.dto.LoginTicketDTO;

import java.util.Date;

/**
 * @author 李志豪
 * @create 2024/5/30
 */
@Mapper
public interface LoginTicketMapper {

    public void addLoginTicketDTO(LoginTicketDTO loginTicketDTO);

    public LoginTicketDTO queryTicketDTO(@Param("userId") String userId);

    public int updateTicketDTO(LoginTicketDTO loginTicketDTO);

    public LoginTicketDTO queryTicketDTOByTicket(@Param("ticket") String ticket);
}
