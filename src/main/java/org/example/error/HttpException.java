package org.example.error;

/**
 * @Author 李志豪
 * @Date 2024/5/29 1:22
 */
public class HttpException extends RuntimeException{
    protected Integer code;

    protected String message;

    public HttpException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
