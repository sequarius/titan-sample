package com.sequarius.titan.sample.system.api;

import com.sequarius.sample.system.api.domain.UserBO;
import com.sequarius.sample.system.api.service.SystemService;
import com.sequarius.titan.sample.common.util.BeanUtils;
import com.sequarius.titan.sample.domain.SysPermissionDO;
import com.sequarius.titan.sample.domain.SysRoleDO;
import com.sequarius.titan.sample.domain.SysUserDO;
import com.sequarius.titan.sample.system.domain.SysUserRequestDTO;
import com.sequarius.titan.sample.system.repository.MetaDataRepository;
import com.sequarius.titan.sample.system.repository.UserRolePermissionRepository;
import com.sequarius.titan.sample.system.service.SysRoleService;
import com.sequarius.titan.sample.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private UserRolePermissionRepository userRolePermissionRepository;

    @Resource
    private MetaDataRepository metaDataRepository;

    @Override
    public UserBO findUser(String username) {
        SysUserDO sysUserDo = sysUserService.findSysUserDo(username);
        if(sysUserDo == null){
            return null;
        }
        UserBO userBO = new UserBO();
        BeanUtils.copyPropertiesIgnoreNull(sysUserDo, userBO);

        List<SysRoleDO> sysRoleDOS = userRolePermissionRepository.listRoles(userBO.getId());
        Set<String> roles = sysRoleDOS.stream()
                .map(SysRoleDO::getRole).collect(Collectors.toSet());
        Set<String> permissions = findAllPermission(sysRoleDOS);
        userBO.setRoles(roles);
        userBO.setPermissions(permissions);
        return userBO;
    }

    @Override
    public Integer saveUser(UserBO userBO) {
        UserBO user = findUser(userBO.getUsername());
        if(user != null){
            return -1;
        }
        SysUserRequestDTO dto = new SysUserRequestDTO();
        BeanUtils.copyPropertiesIgnoreNull(userBO,dto);
        Integer result = sysUserService.saveSysUser(dto);
        userBO.setId(dto.getId());
        return result;
    }

    @Override
    public Integer removeUser(List<Long> ids) {
        return sysUserService.removeSysUser(ids);
    }

    @Override
    public String getFormatMetadataLabel(Long id) {
        return this.getFormatMetadataLabel(id, false);
    }

    @Override
    public String getFormatMetadataLabel(Long id, boolean includeTag) {
        if(id==null){
            return null;
        }
        return metaDataRepository.getFormatMetadataLabel(id, includeTag);

    }

    /**
     * 根据角色及其父角色获取权限
     *
     * @param roles 角色集合
     * @return 所有权限
     */
    private Set<String> findAllPermission(List<SysRoleDO> roles) {
        Set<String> permissions = new TreeSet<>();
        for (SysRoleDO role : roles) {
            Set<String> sysPermissionDOS = userRolePermissionRepository.listPermissions(role.getId())
                    .stream().map(SysPermissionDO::getPermission).collect(Collectors.toSet());
            permissions.addAll(sysPermissionDOS);

            // 获取父角色权限
            while (role.getParentId() != null) {
                role = sysRoleService.findSysRoleDo(role.getParentId());
                sysPermissionDOS = userRolePermissionRepository.listPermissions(role.getId())
                        .stream().map(SysPermissionDO::getPermission).collect(Collectors.toSet());
                permissions.addAll(sysPermissionDOS);
            }
        }
        return permissions;
    }
}
