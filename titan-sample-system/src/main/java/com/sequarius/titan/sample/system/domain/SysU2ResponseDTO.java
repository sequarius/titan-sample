package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sequarius.titan.sample.common.util.Constant;
import com.sequarius.titan.sample.common.util.FormatUtil;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 用户22响应实体
 *
 * @author titan-generator
 * @since 2020-02-23
 */
@Data
@ApiModel("用户22响应实体")
public class SysU2ResponseDTO {

    @ApiModelProperty(value = "id")
    @Max(value = 9223372036854775807L, message = "id不能大于9223372036854775807L")
    private Long id;

    @ApiModelProperty(value = "登录名" , required = true)
    @NotNull(message = "登录名为必填项！")
    @Length(max = 16,message = "登录名不能超过16个字符!")
    private String username;

    @ApiModelProperty(value = "salt")
    @Length(max = 32,message = "salt不能超过32个字符!")
    private String passwordSalt;

    @ApiModelProperty(value = "电话号码" , required = true)
    @NotNull(message = "电话号码为必填项！")
    @Length(max = 11,message = "电话号码不能超过11个字符!")
    private String phoneNumber;

    @ApiModelProperty(value = "预留防坑字段")
    @Max(value = 10000000000L, message = "预留防坑字段不能大于10000000000L")
    private Integer guid;

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
}
