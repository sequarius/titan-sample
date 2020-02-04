package com.sequarius.titan.sample.system.service;

import com.sequarius.titan.sample.common.Page;
import com.sequarius.titan.sample.system.domain.UserRequestDTO;
import com.sequarius.titan.sample.system.domain.UserResponseDTO;

import java.util.List;


public interface UserService {
    List<UserResponseDTO> listUsers(Page page, String keyword);

    Integer saveUser(UserRequestDTO requestDTO);

    Integer updateUser(UserRequestDTO requestDTO);

    UserResponseDTO findUser(Long id);

    Integer removeUser(List<Long> ids);
}
