package com.sequarius.sample.system.api.service;

import com.sequarius.sample.system.api.domain.UserBO;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
public interface SystemService {
    UserBO findUser(String username);
}
