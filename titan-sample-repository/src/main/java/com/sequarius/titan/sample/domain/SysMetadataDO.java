package com.sequarius.titan.sample.domain;

import com.sequarius.titan.sample.common.annonation.Entity;
import com.sequarius.titan.sample.common.annonation.Filed;
import java.util.Date;
import lombok.Data;

/**
 * 元数据
 *
 * @author Michael Chow
 * @date 2020/04/09
 */

@Entity(name = "SysMetadataDO", displayName = "元数据")
@Data
public class SysMetadataDO {
    /**
     * id
     */
    @Filed(name = "id", displayName = "id", length = 20)
    private Long id;

    /**
     * 显示名
     */
    @Filed(name = "label", displayName = "显示名", length = 512)
    private String label;

    /**
     * 内数据集内ID
     */
    @Filed(name = "groupNo", displayName = "内数据集内ID", length = 32)
    private String groupNo;

    /**
     * 元数据集ID
     */
    @Filed(name = "groupId", displayName = "元数据集ID", length = 20)
    private Long groupId;

    /**
     * 
     */
    @Filed(name = "deleted", displayName = "", length = 1)
    private Boolean deleted;

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