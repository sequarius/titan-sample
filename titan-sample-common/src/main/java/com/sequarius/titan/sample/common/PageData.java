package com.sequarius.titan.sample.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 07/02/2020
 */
@Data
@NoArgsConstructor
@ApiModel("分页通用响应体")
public class PageData<T> {

    public PageData(List<T> list, Long total ,Page page) {
        this.total = total;
        this.list = list;
        this.begin = page.getBegin();
        this.length = page.getLength();
    }

    @ApiModelProperty("数据总量")
    private Long total;

    @ApiModelProperty("开始记录")
    private Long begin;

    @ApiModelProperty("查询步长")
    private Long length;

    @ApiModelProperty("列表内容")
    private List<T> list;

}
