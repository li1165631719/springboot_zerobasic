package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.dto.UserDTO;

/**
 * @author 李志豪
 * @create 2024/5/30
 */
@Mapper
public interface UserMapper {

    public void addUserDTO(UserDTO userDTO);

    public UserDTO queryUserDTO(@Param("name") String name);

    public UserDTO queryUserDTOByUserId(@Param("userId") String userId);
}
