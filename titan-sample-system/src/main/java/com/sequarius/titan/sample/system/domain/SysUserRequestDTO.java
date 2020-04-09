package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户请求实体
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Data
@ApiModel("用户请求实体")
public class SysUserRequestDTO {

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

    @ApiModelProperty(value = "密码" , required = true)
    @Length(max = 128,message = "密码不能超过128个字符!")
    private String password;

    @ApiModelProperty(value = "是否被冻结")
    private Boolean locked;

    @ApiModelProperty(value = "是否被删除")
    private Boolean deleted;

    @ApiModelProperty(value = "角色组")
    private List<Long> roleIds;
}
