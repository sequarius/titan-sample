package com.sequarius.domain;

import com.sequarius.titan.sample.annonation.Entity;
import com.sequarius.titan.sample.annonation.Filed;
import lombok.Data;

import java.util.Date;

/**
 * 角色
 *
 * @author Michael Chow
 * @date 2020/01/30
 */

@Entity(name = "SysRoleDO", displayName = "角色")
@Data
public class SysRoleDO {
    /**
     * id
     */
    @Filed(name = "id", displayName = "id", length = 19)
    private Long id;

    /**
     * 角色名称
     */
    @Filed(name = "role", displayName = "角色名称", length = 100)
    private String role;

    /**
     * 角色描述
     */
    @Filed(name = "description", displayName = "角色描述", length = 100)
    private String description;

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