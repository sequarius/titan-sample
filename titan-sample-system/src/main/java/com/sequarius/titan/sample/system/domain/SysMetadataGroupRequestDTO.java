package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 元数据集请求实体
 *
 * @author titan-generator
 * @since 2020-03-20
 */
@Data
@ApiModel("元数据集请求实体")
public class SysMetadataGroupRequestDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "元数据集名称")
    @Length(max = 50,message = "元数据集名称不能超过50个字符!")
    @NotNull(message = "元数据集名称不能为空！")
    private String name;

    @ApiModelProperty(value = "元数据集键")
    @Length(max = 50,message = "元数据键名称不能超过120个字符!")
    @NotNull(message = "元数据键名称不能为空！")
    private String key;

    @ApiModelProperty(value = "元数据集标签")
    @Length(max = 50,message = "元数据集标签不能超过50个字符!")
    private String tag;

    @ApiModelProperty(value = "最小ID长度")
    @Max(value = 9223372036854775807L, message = "最小ID长度不能大于9223372036854775807L")
    private Long minKeyLength;

    @ApiModelProperty(value = "")
    private Boolean deleted;
}
