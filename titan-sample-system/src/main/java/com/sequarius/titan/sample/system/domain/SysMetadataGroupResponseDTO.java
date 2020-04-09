package com.sequarius.titan.sample.system.domain;

import com.sequarius.titan.sample.common.util.Constant;
import com.sequarius.titan.sample.common.util.FormatUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import java.util.Date;


/**
 * 元数据集响应实体
 *
 * @author titan-generator
 * @since 2020-03-20
 */
@Data
@ApiModel("元数据集响应实体")
public class SysMetadataGroupResponseDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "元数据集名称")
    @Length(max = 50,message = "元数据集名称不能超过50个字符!")
    private String name;

    @ApiModelProperty(value = "元数据集标签")
    @Length(max = 50,message = "元数据集标签不能超过50个字符!")
    private String tag;

    @ApiModelProperty(value = "最小ID长度")
    @Max(value = 9223372036854775807L, message = "最小ID长度不能大于9223372036854775807L")
    private Long minKeyLength;

    @ApiModelProperty(value = "元数据组key")
    @Length(max = 120, message = "元数据组key最大不能超过120个字符！")
    private String key;

    @ApiModelProperty(value = "创建日期")
    @DateTimeFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE)
    @JsonFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE, timezone = Constant.DEFAULT_TIME_ZONE)
    private Date createTime;

    @ApiModelProperty(value = "更新日期")
    @DateTimeFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE)
    @JsonFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE, timezone = Constant.DEFAULT_TIME_ZONE)
    private Date updateTime;
}
