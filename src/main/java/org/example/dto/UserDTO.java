package org.example.dto;

/**
 * @author 李志豪
 * @create 2024/5/30
 */
public class UserDTO {
    private String id;
    private String name;
    private String password;
    /**
     * 密码加盐
     */
    private String salt;
    /**
     * 用户头像
     */
    private String headUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
