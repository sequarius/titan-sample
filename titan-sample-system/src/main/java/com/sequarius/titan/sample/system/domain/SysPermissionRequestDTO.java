package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;

/**
 * 权限请求实体
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Data
@ApiModel("权限请求实体")
public class SysPermissionRequestDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "描述")
    @Length(max = 100,message = "描述不能超过100个字符!")
    private String description;

    @ApiModelProperty(value = "分组")
    @Length(max = 100,message = "分组不能超过100个字符!")
    private String group;
}
