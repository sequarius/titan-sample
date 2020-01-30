package com.sequarius.common;

import lombok.Data;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 31/01/2020
 */
@Data
public class Response<T> {
    private String message;
    private Boolean result;
    private T data;

    private Response(String message, Boolean result, T data) {
        this.message = message;
        this.result = result;
        this.data = data;
    }

    public static <T> Response<T> success(T data, String message) {
        return new Response<>(message, true, data);
    }

    public static <T> Response<T> success(T data) {
        return success(data, "操作成功!");
    }

    public static <T> Response<T> success(String message) {
        return success(null, message);
    }

    public static <T> Response<T> fail(T data, String message) {
        return new Response<>(message, false, data);
    }

    public static <T> Response<T> fail(T data) {
        return fail(data, "操作失败!");
    }

    public static <T> Response<T> fail(String message) {
        return fail(null, message);
    }
}
