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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


/**
 * 用户响应实体
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Data
@ApiModel("用户响应实体")
public class SysUserResponseDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "登录名" , required = true)
    @NotNull(message = "登录名为必填项！")
    @Length(max = 16,message = "登录名不能超过16个字符!")
    private String username;

    @ApiModelProperty(value = "姓名" , required = true)
    @NotNull(message = "姓名为必填项！")
    @Length(max = 16,message = "姓名不能超过16个字符!")
    private String name;

    @ApiModelProperty(value = "是否被冻结")
    private Boolean locked;

    @ApiModelProperty(value = "是否被删除")
    private Boolean deleted;

    @ApiModelProperty(value = "ip")
    @Length(max = 64,message = "ip不能超过64个字符!")
    private String lastSignInIp;

    @ApiModelProperty(value = "创建日期")
    @DateTimeFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE)
    @JsonFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE, timezone = Constant.DEFAULT_TIME_ZONE)
    private Date createTime;

    @ApiModelProperty(value = "更新日期")
    @DateTimeFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE)
    @JsonFormat(pattern = FormatUtil.DATE_FORMAT_TEMPLATE, timezone = Constant.DEFAULT_TIME_ZONE)
    private Date updateTime;

    @ApiModelProperty(value = "角色集合")
    private List<Entry<String,String>> roles;
}
