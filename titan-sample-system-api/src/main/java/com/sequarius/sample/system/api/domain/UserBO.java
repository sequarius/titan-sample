package com.sequarius.sample.system.api.domain;

import lombok.Data;

import java.util.Set;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
@Data
public class UserBO {
    private String username;
    private String password;
    private String name;
    private Long id;
    // 是否被锁定
    private Boolean locked;
    // 是否已离职
    private Boolean checkIn;
    // 角色结果集
    private Set<String> roles;
    // 权限结果集
    private Set<String> permissions;
}