package com.sequarius.titan.sample.common;

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

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(message, true, data);
    }

    public static <T> Response<T> success(T data) {
        return success("操作成功!", data);
    }

    public static <T> Response<T> success(String message) {
        return success(message, null);
    }

    public static <T> Response<T> fail(String message, T data) {
        return new Response<>(message, false, data);
    }

    public static <T> Response<T> fail(T data) {
        return fail("操作失败!", data);
    }

    public static <T> Response<T> fail(String message) {
        return fail(message, null);
    }
}
