package com.sequarius.titan.sample.system.service;

import com.sequarius.common.Page;
import com.sequarius.titan.sample.system.domain.UserResponseDTO;

import java.util.List;




/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
public interface UserService {
    List<UserResponseDTO> listUsers(Page page, String keyword);
}
