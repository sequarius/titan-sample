package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
@Data
@ApiModel("登录请求")
public class LoginRequestDTO {

    @ApiModelProperty("用户名")
    @Length(max = 16, min = 1, message = "用户名必须为1-16个字符！")
    private String username;

    @ApiModelProperty("密码")
    @Length(max = 16, min = 6, message = "密码必须为6-16个字符！")
    private String password;
}
