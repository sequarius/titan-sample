package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.titan.sample.common.Page;
import com.sequarius.titan.sample.common.PageData;
import com.sequarius.titan.sample.common.util.BeanUtils;
import com.sequarius.titan.sample.domain.SysUserDO;
import com.sequarius.titan.sample.domain.SysUserDOExample;
import com.sequarius.titan.sample.repository.SysUserDOMapper;
import com.sequarius.titan.sample.system.domain.UserRequestDTO;
import com.sequarius.titan.sample.system.domain.UserResponseDTO;
import com.sequarius.titan.sample.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserDOMapper userMapper;

    @Override
    public PageData<UserResponseDTO> listUsers(Page page, String keyword) {
        SysUserDOExample example = new SysUserDOExample();
        example.setPage(page);
        if (!StringUtils.isEmpty(keyword)) {
            example.or().andDeletedEqualTo(false).andUsernameLike(keyword + "%");
            example.or().andDeletedEqualTo(false).andPhoneNumberLike(keyword+"%");
        }else{
            example.or().andDeletedEqualTo(false);
        }
        List<UserResponseDTO> data = BeanUtils.copyList(userMapper.selectByExample(example), UserResponseDTO.class);
        long totalCount = userMapper.countByExample(example);
        return new PageData<>(data,totalCount,page);
    }

    @Override
    public Integer saveUser(UserRequestDTO requestDTO) {
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
    public Integer removeUser(List<Long> ids) {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setDeleted(true);
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andIdIn(ids).andDeletedEqualTo(false);
        return userMapper.updateByExampleSelective(sysUserDO,example);
    }

    private SysUserDO findUserDOById(Long id) {
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andDeletedEqualTo(false).andIdEqualTo(id);
        List<SysUserDO> sysUserDOS = userMapper.selectByExample(example);
        if (sysUserDOS.size() != 1) {
            log.warn("try to find {} with id {}, but find {}","SysUserDO",id,sysUserDOS);
            return null;
        }
        return sysUserDOS.get(0);
    }
}
