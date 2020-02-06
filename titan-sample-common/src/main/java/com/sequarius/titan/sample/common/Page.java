package com.sequarius.titan.sample.common;

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
public class Page {
    @Min(value = 0,message = "查询起点不能小于0！")
    private Integer begin = 0;
    @Min(value = 0,message = "分页长度不能小于0！")
    @Max(value = 100,message = "分页长度不能大于100！")
    private Integer length =10;
}
