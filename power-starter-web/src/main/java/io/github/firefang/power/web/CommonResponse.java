package io.github.firefang.power.web;

/**
 * Common response of HTTP
 * 
 * @author xinufo
 *
 */
public class CommonResponse<T> {
    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int INTERNAL_ERROR = 500;
    public static final int BUSINESS_FAIL = 600;

    public static final String MSG_SUCCESS = "ok";

    private int code;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success() {
        return success(MSG_SUCCESS);
    }

    public static <T> CommonResponse<T> success(String message) {
        CommonResponse<T> resp = new CommonResponse<>();
        resp.setCode(SUCCESS);
        resp.setMessage(message);
        return resp;
    }

    public static <T> CommonResponse<T> fail(int code, String message) {
        CommonResponse<T> resp = new CommonResponse<>();
        resp.setCode(code);
        resp.setMessage(message);
        return resp;
    }

    public CommonResponse<T> data(T data) {
        this.setData(data);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    @Override
    public String toString() {
        return "CommonResponse [code=" + code + ", message=" + message + ", data=" + data + "]";
    }

}
