package com.sequarius.titan.sample.system.repository;

import com.sequarius.titan.sample.domain.SysPermissionDO;
import com.sequarius.titan.sample.domain.SysRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * project erp-bundle
 *
 * @author Sequarius *
 * @since 03/03/2020
 */
public interface UserRolePermissionRepository {

    List<SysPermissionDO> listPermissions(@Param("roleId") Long roleId);

    List<SysRoleDO> listRoles(@Param("userId") Long userId);

}
