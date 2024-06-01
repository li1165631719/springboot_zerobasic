package org.example.local;

import org.example.vo.UserVO;
import org.springframework.stereotype.Component;

/**
 * @Author 李志豪
 * @Date 2024/6/2 5:16
 */
@Component
public class HostHolder {
    private static ThreadLocal<UserVO> users= new ThreadLocal<>();

    public UserVO getUser(){
        return users.get();
    }
    public void setUser(UserVO user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }
}
