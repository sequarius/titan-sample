package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.common.Page;
import com.sequarius.domain.SysUserDOExample;
import com.sequarius.repository.SysUserDOMapper;
import com.sequarius.titan.sample.system.domain.UserResponseDTO;
import com.sequarius.titan.sample.system.service.UserService;
import com.sequarius.util.ListBeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private SysUserDOMapper userMapper;
    @Override
    public List<UserResponseDTO> listUsers(Page page, String keyword) {
        return ListBeanUtils.copyList(userMapper.selectByExample(new SysUserDOExample()),UserResponseDTO.class);
    }
}
