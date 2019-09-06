package com.github.xjjdog.seckill.demo.common.util;


import com.github.xjjdog.seckill.demo.common.model.RepStatus;
import com.github.xjjdog.seckill.demo.common.model.ResponseData;

/**
 * 消息返回生成
 */
public class ResponseUtil {


    public static <T> ResponseData<T> custom(Integer code, String message) {
        return new ResponseData<>(code, message, System.currentTimeMillis());
    }

    public static <T> ResponseData<T> custom(Integer code, String message, T data) {
        return new ResponseData<>(code, message, data, System.currentTimeMillis());
    }

    public static <T> ResponseData<T> success() {
        return success(null);
    }

    public static <T> ResponseData<T> success(T data) {
        return success(null, data);
    }

    public static <T> ResponseData<T> success(String message) {
        return new ResponseData<>(RepStatus.SUCCESS, message, System.currentTimeMillis());
    }

    public static <T> ResponseData<T> success(String message, T data) {
        return new ResponseData<>(RepStatus.SUCCESS, message, data, System.currentTimeMillis());
    }


    public static <T> ResponseData<T> fail() {
        return fail("处理失败");
    }

    public static <T> ResponseData<T> fail(String msg) {
        return new ResponseData<>(RepStatus.FAIL, msg, System.currentTimeMillis());
    }

    public static <T> ResponseData<T> fail(String msg, T data) {
        return new ResponseData<>(RepStatus.FAIL, msg, data, System.currentTimeMillis());
    }

    public static <T> ResponseData<T> error() {
        return error("出错了");
    }

    public static <T> ResponseData<T> error(String msg) {
        return new ResponseData<>(RepStatus.ERROR, msg, System.currentTimeMillis());
    }

    public static <T> ResponseData<T> error(String msg, T data) {
        return new ResponseData<>(RepStatus.ERROR, msg, data, System.currentTimeMillis());
    }

    public static boolean ok(ResponseData responseData) {

        return responseData != null && RepStatus.SUCCESS.equals(responseData.getCode());
    }

}
