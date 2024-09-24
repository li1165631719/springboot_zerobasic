package org.example.message;

/**
 * @Author 李志豪
 * @Date 2024/9/22 16:47
 */
public interface MessageManage {

    public void dealMessage(AsyncMessageDTO asyncMessageDTO);

    public String getMessageType();
}
