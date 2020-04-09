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
 * 权限响应实体
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Data
@ApiModel("权限响应实体")
public class SysPermissionResponseDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "权限")
    @Length(max = 100,message = "权限不能超过100个字符!")
    private String permission;

    @ApiModelProperty(value = "描述")
    @Length(max = 100,message = "描述不能超过100个字符!")
    private String description;

    @ApiModelProperty(value = "分组")
    @Length(max = 100,message = "分组不能超过100个字符!")
    private String group;

    @ApiModelProperty(value = "创建日期")
    @DateTimeFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE)
    @JsonFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE, timezone = Constant.DEFAULT_TIME_ZONE)
    private Date createTime;

    @ApiModelProperty(value = "更新日期")
    @DateTimeFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE)
    @JsonFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE, timezone = Constant.DEFAULT_TIME_ZONE)
    private Date updateTime;
}
