package com.sequarius.titan.sample.system.service;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.TreeNodeDTO;
import com.sequarius.titan.sample.domain.SysRoleDO;
import com.sequarius.titan.sample.system.domain.SysRoleRequestDTO;
import com.sequarius.titan.sample.system.domain.SysRoleResponseDTO;

import java.util.List;

/**
 * 角色 服务
 *
 * @author titan-generator
 * @since 2020-03-02
 */
public interface SysRoleService {

    /**
     * 获取角色列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 角色列表
     */
    PageData<SysRoleResponseDTO> listSysRoles(Page page, String keyword);

    /**
     * 新增角色
     *
     * @param requestDTO 角色请求实体
     * @return 操作成功数量
     */
    Integer saveSysRole(SysRoleRequestDTO requestDTO);

    /**
     * 更新角色
     *
     * @param requestDTO 角色请求实体
     * @return 操作成功数量
     */
    Integer updateSysRole(SysRoleRequestDTO requestDTO);

    /**
     * 获取角色
     *
     * @param id 角色id
     * @return 角色响应实体
     */
    SysRoleResponseDTO findSysRole(Long id);

    /**
     * 删除角色
     *
     * @param ids 删除角色id列表
     * @return 删除成功数量
     */
    Integer removeSysRole(List<Long> ids);


    /**
     * 根据role 查询角色
     * @param role role
     * @return 角色DO
     */
    SysRoleDO findSysRoleDo(String role);


    /**
     * 根据id 查询角色DO
     * @param id 角色ID
     * @return 角色DO
     */
    SysRoleDO findSysRoleDo(Long id);

    /**
     * 获取角色列表
     * @param keyword 搜索关键字
     * @return 角色列表树
     */
    List<TreeNodeDTO> roleTree(String keyword);


    /**
     * 保存角色权限的关联关系
     * @param roleId 角色id
     * @param permissionIds  权限id
     * @return 保存数量
     */
    Integer saveRolePermissionRelation(Long roleId, List<Long> permissionIds);


    /**
     * 获取循环依赖关系
     * @param requestDTO request
     * @return 循环关系 null如果没有环
     */
    String findCircleDependency(SysRoleRequestDTO requestDTO);

}
