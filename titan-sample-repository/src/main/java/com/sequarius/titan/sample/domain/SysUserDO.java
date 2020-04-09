package com.sequarius.titan.sample.domain;

import com.sequarius.titan.sample.common.annonation.Entity;
import com.sequarius.titan.sample.common.annonation.Filed;
import lombok.Data;

import java.util.Date;

/**
 * 用户
 *
 * @author Michael Chow
 * @date 2020/04/09
 */

@Entity(name = "SysUserDO", displayName = "用户")
@Data
public class SysUserDO {
    /**
     * id
     */
    @Filed(name = "id", displayName = "id", length = 19)
    private Long id;

    /**
     * 登录名
     */
    @Filed(name = "username", displayName = "登录名", length = 16)
    private String username;

    /**
     * 姓名
     */
    @Filed(name = "name", displayName = "姓名", length = 16)
    private String name;

    /**
     * 密码
     */
    @Filed(name = "password", displayName = "密码", length = 128)
    private String password;

    /**
     * salt
     */
    @Filed(name = "passwordSalt", displayName = "salt", length = 32)
    private String passwordSalt;

    /**
     * 电话号码
     */
    @Filed(name = "phoneNumber", displayName = "电话号码", length = 11)
    private String phoneNumber;

    /**
     * 预留防坑字段
     */
    @Filed(name = "guid", displayName = "预留防坑字段", length = 10)
    private Integer guid;

    /**
     * 是否被冻结
     */
    @Filed(name = "locked", displayName = "是否被冻结", length = 1)
    private Boolean locked;

    /**
     * 是否被删除
     */
    @Filed(name = "deleted", displayName = "是否被删除", length = 1)
    private Boolean deleted;

    /**
     * ip
     */
    @Filed(name = "lastSignInIp", displayName = "ip", length = 64)
    private String lastSignInIp;

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