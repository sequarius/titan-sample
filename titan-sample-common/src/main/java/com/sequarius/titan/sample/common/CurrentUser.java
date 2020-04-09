package com.sequarius.titan.sample.common;

import lombok.Data;
import org.apache.shiro.SecurityUtils;

import java.util.Set;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
@Data
public class CurrentUser {

    private long id;

    private String username;

    private String name;

    private String avatar;

    private Set<String> roles;

    private Set<String> permissions;

    public static CurrentUser getCurrentUser(){
        return (CurrentUser) SecurityUtils.getSubject().getPrincipal();
    }
}
