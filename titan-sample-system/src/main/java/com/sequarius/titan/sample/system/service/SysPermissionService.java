package com.sequarius.titan.sample.system.service;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.TreeNodeDTO;
import com.sequarius.titan.sample.domain.SysPermissionDO;
import com.sequarius.titan.sample.system.domain.SysPermissionRequestDTO;
import com.sequarius.titan.sample.system.domain.SysPermissionResponseDTO;

import java.util.List;

/**
 * 权限 服务
 *
 * @author titan-generator
 * @since 2020-03-02
 */
public interface SysPermissionService {

    /**
     * 获取权限列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 权限列表
     */
    PageData<SysPermissionResponseDTO> listSysPermissions(Page page, String keyword);

    /**
     * 新增权限
     *
     * @param requestDTO 权限请求实体
     * @return 操作成功数量
     */
    Integer saveSysPermission(SysPermissionRequestDTO requestDTO);

    /**
     * 更新权限
     *
     * @param requestDTO 权限请求实体
     * @return 操作成功数量
     */
    Integer updateSysPermission(SysPermissionRequestDTO requestDTO);

    /**
     * 获取权限
     *
     * @param id 权限id
     * @return 权限响应实体
     */
    SysPermissionResponseDTO findSysPermission(Long id);

    /**
     * 删除权限
     *
     * @param ids 删除权限id列表
     * @return 删除成功数量
     */
    Integer removeSysPermission(List<Long> ids);


    /**
     * 获取权限
     * @param permission 权限
     * @return 权限DO
     */
    SysPermissionDO findSysPermission(String permission);


    /**
     * 保存权限DO
     * @param permissionDO 权限DO
     * @return 保存成功数量
     */
    Integer saveSysPermissionDO(SysPermissionDO permissionDO);


    /**
     * 获取权限树
     * @param keyword 关键字
     * @return 权限树
     */
    List<TreeNodeDTO> getPermissionTree(String keyword);

}
