package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
@Data
@ApiModel("用户")
public class UserRequestDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("登录名")
    private String username;

    @ApiModelProperty("密码")
    @Length(max = 16, min = 6, message = "密码必须在6-16位之间")
    private String password;


    @ApiModelProperty("电话号码")
    @Length(max = 11, min = 11, message = "手机号必须为11位")
    private String phoneNumber;


    @ApiModelProperty("是否被冻结")
    private Boolean locked;


    @ApiModelProperty("ip")
    private String lastSignInIp;

}
