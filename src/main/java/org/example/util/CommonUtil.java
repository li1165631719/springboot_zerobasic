package org.example.util;

import java.util.UUID;

/**
 * @Author 李志豪
 * @Date 2024/5/28 0:36
 */
public class CommonUtil {

    //自动生成uuid
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //MD5加密
    public static String MD5(String key)  {
        try {
            return MD5Util.GetMD5Code(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
