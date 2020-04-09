package com.sequarius.titan.sample.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sequarius.titan.sample.common.domain.Entry;
import com.sequarius.titan.sample.common.util.Constant;
import com.sequarius.titan.sample.common.util.FormatUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;


/**
 * 角色响应实体
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Data
@ApiModel("角色响应实体")
public class SysRoleResponseDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    @Length(max = 100,message = "角色名称不能超过100个字符!")
    private String role;

    @ApiModelProperty(value = "父角色id")
    @Max(value = 9223372036854775807L, message = "父角色id不能大于9223372036854775807L")
    private Long parentId;

    @ApiModelProperty(value = "角色描述")
    @Length(max = 100,message = "角色描述不能超过100个字符!")
    private String description;

    @ApiModelProperty(value = "创建日期")
    @DateTimeFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE)
    @JsonFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE, timezone = Constant.DEFAULT_TIME_ZONE)
    private Date createTime;

    @ApiModelProperty(value = "更新日期")
    @DateTimeFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE)
    @JsonFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE, timezone = Constant.DEFAULT_TIME_ZONE)
    private Date updateTime;

    @ApiModelProperty(value = "权限集合")
    private List<Entry<String,String>> permissions;

}
