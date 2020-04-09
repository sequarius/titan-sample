package com.sequarius.titan.sample.domain;

import com.sequarius.titan.sample.common.annonation.Entity;
import com.sequarius.titan.sample.common.annonation.Filed;
import java.util.Date;
import lombok.Data;

/**
 * 用户角色关系
 *
 * @author Michael Chow
 * @date 2020/04/09
 */

@Entity(name = "SysUserRoleDO", displayName = "用户角色关系")
@Data
public class SysUserRoleDO {
    /**
     * id
     */
    @Filed(name = "id", displayName = "id", length = 19)
    private Long id;

    /**
     * 管理员id
     */
    @Filed(name = "userId", displayName = "管理员id", length = 19)
    private Long userId;

    /**
     * 角色id
     */
    @Filed(name = "roleId", displayName = "角色id", length = 19)
    private Long roleId;

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