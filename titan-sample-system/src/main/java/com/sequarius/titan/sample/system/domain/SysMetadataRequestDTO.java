package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;

/**
 * 元数据请求实体
 *
 * @author titan-generator
 * @since 2020-03-20
 */
@Data
@ApiModel("元数据请求实体")
public class SysMetadataRequestDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "显示名")
    @Length(max = 512,message = "显示名不能超过512个字符!")
    private String label;

    @ApiModelProperty(value = "内数据集内ID")
    @Length(max = 32,message = "内数据集内ID不能超过32个字符!")
    private String groupNo;

    @ApiModelProperty(value = "元数据集ID")
    @Max(value = 9223372036854775807L, message = "元数据集ID不能大于9223372036854775807L")
    private Long groupId;
}
