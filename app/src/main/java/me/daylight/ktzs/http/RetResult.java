package me.daylight.ktzs.http;

public class RetResult<T> {

    private int code;

    private String msg;

    private T data;

    public RetResult<T> setCode(RetCode retCode) {
        this.code = retCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RetResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RetResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RetResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public enum RetCode {

        // 成功
        SUCCESS(200),

        // 失败
        FAIL(400),

        // 未认证（签名错误）
        UNAUTHORIZED(401),

        // 接口不存在
        NOT_FOUND(404),

        // 服务器内部错误
        INTERNAL_SERVER_ERROR(500);

        public int code;

        RetCode(int code) {
            this.code = code;
        }
    }
}