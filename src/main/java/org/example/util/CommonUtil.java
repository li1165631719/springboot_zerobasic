package org.example.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 普通工具类
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

    public static String getNowDayFormat(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        System.out.println(CommonUtil.getNowDayFormat(new Date()));
    }
}
