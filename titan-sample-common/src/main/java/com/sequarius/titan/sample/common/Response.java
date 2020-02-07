package com.sequarius.titan.sample.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 31/01/2020
 */
@Data
@ApiModel("通用响应体")
public class Response<T> {

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("操作结果")
    private Boolean result;

    @ApiModelProperty("响应数据")
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
