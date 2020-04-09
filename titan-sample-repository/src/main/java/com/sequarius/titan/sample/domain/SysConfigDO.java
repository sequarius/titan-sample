package com.sequarius.titan.sample.domain;

import com.sequarius.titan.sample.common.annonation.Entity;
import com.sequarius.titan.sample.common.annonation.Filed;
import java.util.Date;
import lombok.Data;

/**
 * 系统配置
 *
 * @author Michael Chow
 * @date 2020/04/09
 */

@Entity(name = "SysConfigDO", displayName = "系统配置")
@Data
public class SysConfigDO {
    /**
     * id
     */
    @Filed(name = "id", displayName = "id", length = 19)
    private Long id;

    /**
     * 配置项
     */
    @Filed(name = "key", displayName = "配置项", length = 128)
    private String key;

    /**
     * 公开配置
     */
    @Filed(name = "publicConfig", displayName = "公开配置", length = 1)
    private Boolean publicConfig;

    /**
     * 配置描述
     */
    @Filed(name = "describe", displayName = "配置描述", length = 256)
    private String describe;

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

    /**
     * 配置值
     */
    @Filed(name = "value", displayName = "配置值", length = 65535)
    private String value;
}