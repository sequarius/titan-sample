package com.sequarius.titan.sample.domain;

import com.sequarius.titan.sample.common.annonation.Entity;
import com.sequarius.titan.sample.common.annonation.Filed;
import java.util.Date;
import lombok.Data;

/**
 * 元数据集
 *
 * @author Michael Chow
 * @date 2020/04/09
 */

@Entity(name = "SysMetadataGroupDO", displayName = "元数据集")
@Data
public class SysMetadataGroupDO {
    /**
     * id
     */
    @Filed(name = "id", displayName = "id", length = 20)
    private Long id;

    /**
     * 元数据集名称
     */
    @Filed(name = "name", displayName = "元数据集名称", length = 50)
    private String name;

    /**
     * 元数据集标签
     */
    @Filed(name = "tag", displayName = "元数据集标签", length = 50)
    private String tag;

    /**
     * 数据键名
     */
    @Filed(name = "key", displayName = "数据键名", length = 120)
    private String key;

    /**
     * 最小ID长度
     */
    @Filed(name = "minKeyLength", displayName = "最小ID长度", length = 19)
    private Long minKeyLength;

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