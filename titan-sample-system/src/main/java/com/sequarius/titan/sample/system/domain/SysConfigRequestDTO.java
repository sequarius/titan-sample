package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;

/**
 * 系统配置请求实体
 *
 * @author titan-generator
 * @since 2020-03-19
 */
@Data
@ApiModel("系统配置请求实体")
public class SysConfigRequestDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "配置项")
    @Length(max = 128,message = "配置项不能超过128个字符!")
    private String key;

    @ApiModelProperty(value = "公开配置")
    private Boolean publicConfig;

    @ApiModelProperty(value = "配置描述")
    @Length(max = 256,message = "配置描述不能超过256个字符!")
    private String describe;

    @ApiModelProperty(value = "配置值")
    @Length(max = 65535,message = "配置值不能超过65,535个字符!")
    private String value;
}
