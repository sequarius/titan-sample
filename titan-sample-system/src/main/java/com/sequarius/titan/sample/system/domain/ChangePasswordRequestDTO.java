package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * project erp-bundle
 *
 * @author JiangDingMing *
 * @since 23/03/2020
 */
@Data
@ApiModel("修改密码请求")
public class ChangePasswordRequestDTO {

    @ApiModelProperty("旧密码")
    @NotNull(message="旧密码不能为空")
    @Length(max = 128, min = 6, message = "旧密码必须为6-128个字符！")
    private String originPassword;

    @ApiModelProperty("新密码")
    @NotNull(message="新密码不能为空")
    @Length(max = 128, min = 6, message = "新密码必须为6-128个字符！")
    private String password;

}
