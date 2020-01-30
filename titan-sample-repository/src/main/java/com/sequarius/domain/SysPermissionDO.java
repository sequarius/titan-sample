package com.sequarius.domain;

import com.sequarius.annonation.Entity;
import com.sequarius.annonation.Filed;
import java.util.Date;
import lombok.Data;

/**
 * 权限
 *
 * @author Michael Chow
 * @date 2020/01/30
 */

@Entity(name = "SysPermissionDO", displayName = "权限")
@Data
public class SysPermissionDO {
    /**
     * id
     */
    @Filed(name = "id", displayName = "id", length = 19)
    private Long id;

    /**
     * 权限
     */
    @Filed(name = "permission", displayName = "权限", length = 100)
    private String permission;

    /**
     * 描述
     */
    @Filed(name = "description", displayName = "描述", length = 100)
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