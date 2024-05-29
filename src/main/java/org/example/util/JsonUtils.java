package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @author 李志豪
 * @create 2024/5/29
 */
public class JsonUtils {

    //定义jackson对象
    private static final ObjectMapper MAPPER =new ObjectMapper();

    /**
     * 将对象转换成json字符串
     * @param data
     * @return
     * */
    public static String objectToJson(Object data){

        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    /**
     * 将json结果转换为对象
     * @param jsonData json数据
     * @param beanType json数据
     * @return
     * 泛型
     * <T> T返回值是一个T类型，这个T是<T> T中的第二个T，告诉编辑器，我传递什么你就给我返回什么样的数据类型。
     * */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType){//

        try {
            T t = MAPPER.readValue(jsonData,beanType);
            return t;
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果转换为对象
     * @param jsonData json数据
     * @param beanType json数据
     * @return
     * */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType){//
        JavaType javaType= MAPPER.getTypeFactory().constructParametricType(List.class,beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData,javaType);
            return list;
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
