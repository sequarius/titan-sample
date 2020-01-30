package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
@Data
@ApiModel("用户")
public class UserResponseDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("登录名")
    private String username;


    @ApiModelProperty("电话号码")
    private String phoneNumber;


    @ApiModelProperty("是否被冻结")
    private Boolean locked;


    @ApiModelProperty("ip")
    private String lastSignInIp;

}
