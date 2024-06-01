package org.example.common;

import org.example.error.CommonExceptionEnum;
import org.example.error.ExceptionCode;
import org.example.error.HttpException;

/**
 * @Author 李志豪
 * @Date 2024/5/29 1:26
 */
public class HttpResult<T> {
    private Integer code;
    private String message;
    private T data;

    public HttpResult() {
    }

    public HttpResult(T data) {
        this.setCode(CommonExceptionEnum.SUCCESS.getCode());
        this.setMessage(CommonExceptionEnum.SUCCESS.getMessage());
        this.data =data;
    }

    public HttpResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static HttpResult ok(){
        HttpResult result =new HttpResult();
        result.setCode(CommonExceptionEnum.SUCCESS.getCode());
        result.setMessage(CommonExceptionEnum.SUCCESS.getMessage());
        return  result;
    }

    public static HttpResult fail(){
        HttpResult result =new HttpResult();
        result.setCode(CommonExceptionEnum.FAIL.getCode());
        result.setMessage(CommonExceptionEnum.FAIL.getMessage());
        return  result;
    }

    public static HttpResult generateHttpResult(ExceptionCode exceptionCode){
        HttpResult result =new HttpResult();
        result.setCode(exceptionCode.getCode());
        result.setMessage(exceptionCode.getMessage());
        return  result;
    }

    public static HttpResult generateExceptionHttpResult(HttpException httpException){
        HttpResult result =new HttpResult();
        result.setCode(httpException.getCode());
        result.setMessage(httpException.getMessage());
        return  result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
