package com.sequarius.common;

import lombok.Data;

import java.util.Set;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
@Data
public class CurrentUser {

    private long userId;

    private String username;

    private Set<String> roles;

    private Set<String> permissions;
}
