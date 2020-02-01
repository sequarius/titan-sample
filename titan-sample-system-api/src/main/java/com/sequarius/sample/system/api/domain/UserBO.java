package com.sequarius.sample.system.api.domain;

import lombok.Data;

import java.util.Set;

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
    private Long id;
    private Set<String> roles;
    private Set<String> permissions;
}
