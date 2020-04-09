package com.sequarius.titan.sample.domain;

import com.sequarius.titan.sample.common.annonation.Entity;
import com.sequarius.titan.sample.common.annonation.Filed;
import java.util.Date;
import lombok.Data;

/**
 * 角色权限关系
 *
 * @author Michael Chow
 * @date 2020/04/09
 */

@Entity(name = "SysRolePermissionDO", displayName = "角色权限关系")
@Data
public class SysRolePermissionDO {
    /**
     * id
     */
    @Filed(name = "id", displayName = "id", length = 19)
    private Long id;

    /**
     * 角色id
     */
    @Filed(name = "roleId", displayName = "角色id", length = 19)
    private Long roleId;

    /**
     * 权限id
     */
    @Filed(name = "permissionId", displayName = "权限id", length = 19)
    private Long permissionId;

    /**
     * 创建日期
     */
    @Filed(name = "createTime", displayName = "创建日期", length = 19)
    private Date createTime;

    /**
     * 更新日期
     */
    @Filed(name = "updateTime", displayName = "更新日期", length = 19)
    private Date updateTime;
}