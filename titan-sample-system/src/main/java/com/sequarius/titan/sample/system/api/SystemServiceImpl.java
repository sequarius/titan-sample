package com.sequarius.titan.sample.system.api;

import com.sequarius.domain.SysUserDO;
import com.sequarius.domain.SysUserDOExample;
import com.sequarius.repository.SysUserDOMapper;
import com.sequarius.sample.system.api.domain.UserBO;
import com.sequarius.sample.system.api.service.SystemService;
import com.sequarius.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
    @Resource
    private SysUserDOMapper userDOMapper;

    @Override
    public UserBO findUser(String username) {
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<SysUserDO> sysUserDOS = userDOMapper.selectByExample(example);
        if (sysUserDOS.isEmpty()) {
            return null;
        }

        if (sysUserDOS.size() != 1) {
            log.warn("find more than 1 user -> {} with username {}", sysUserDOS, username);
            return null;
        }
        UserBO userBO = new UserBO();
        BeanUtils.copyPropertiesIgnoreNull(sysUserDOS.get(0), userBO);
        return userBO;
    }
}
