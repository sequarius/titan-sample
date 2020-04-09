package com.sequarius.titan.sample.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * project titan
 *
 * @author Sequarius *
 * @since 12/01/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询参数")
public class Page {

    @ApiModelProperty("查询起点")
    @Min(value = 0,message = "查询起点不能小于0！")
    private Long begin = 0L;

    @ApiModelProperty("查询步长")
    @Min(value = 0,message = "分页长度不能小于0！")
    @Max(value = 100,message = "分页长度不能大于100！")
    private Long length =10L;
}
