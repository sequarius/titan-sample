package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.common.Page;
import com.sequarius.domain.SysUserDO;
import com.sequarius.domain.SysUserDOExample;
import com.sequarius.repository.SysUserDOMapper;
import com.sequarius.titan.sample.system.domain.UserRequestDTO;
import com.sequarius.titan.sample.system.domain.UserResponseDTO;
import com.sequarius.titan.sample.system.service.UserService;
import com.sequarius.util.BeanUtils;
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
        return BeanUtils.copyList(userMapper.selectByExample(new SysUserDOExample()), UserResponseDTO.class);
    }

    @Override
    public Integer addUser(UserRequestDTO requestDTO) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(requestDTO, sysUserDO);
        return userMapper.insertSelective(sysUserDO);
    }

    @Override
    public Integer updateUser(UserRequestDTO requestDTO) {
        SysUserDO sysUserDO = findUserDOById(requestDTO.getId());
        if (sysUserDO == null) {
            return -1;
        }
        BeanUtils.copyPropertiesIgnoreNull(requestDTO, sysUserDO);
        return userMapper.updateByPrimaryKeySelective(sysUserDO);
    }

    @Override
    public UserResponseDTO findUser(Long id) {
        SysUserDO sysUserDO = findUserDOById(id);
        if (sysUserDO == null) {
            return null;
        }
        UserResponseDTO userDTO = new UserResponseDTO();
        BeanUtils.copyProperties(sysUserDO, userDTO);
        return userDTO;
    }

    @Override
    public Integer deleteUser(List<Long> ids) {
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andIdIn(ids);
        return userMapper.deleteByExample(example);
    }

    private SysUserDO findUserDOById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
