package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * 角色请求实体
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Data
@ApiModel("角色请求实体")
public class SysRoleRequestDTO {

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

    @ApiModelProperty(value = "权限组")
    private List<Long> permissionIds;
}
