package com.sequarius.titan.sample.system.service;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.domain.SysUserDO;
import com.sequarius.titan.sample.system.domain.SysUserRequestDTO;
import com.sequarius.titan.sample.system.domain.SysUserResponseDTO;

import java.util.List;
import java.util.Set;

/**
 * 用户 服务
 *
 * @author titan-generator
 * @since 2020-03-02
 */
public interface SysUserService {

    /**
     * 获取用户列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @param ids 用户id列表
     * @return 用户列表
     */
    PageData<SysUserResponseDTO> listSysUsers(Page page, String keyword, Set<Long> ids);

    /**
     * 新增用户
     *
     * @param requestDTO 用户请求实体
     * @return 操作成功数量
     */
    Integer saveSysUser(SysUserRequestDTO requestDTO);

    /**
     * 更新用户
     *
     * @param requestDTO 用户请求实体
     * @return 操作成功数量
     */
    Integer updateSysUser(SysUserRequestDTO requestDTO);

    /**
     * 获取用户
     *
     * @param id 用户id
     * @return 用户响应实体
     */
    SysUserResponseDTO findSysUser(Long id);

    /**
     * 删除用户
     *
     * @param ids 删除用户id列表
     * @return 删除成功数量
     */
    Integer removeSysUser(List<Long> ids);

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return 用户响应实体
     */
    SysUserResponseDTO findSysUser(String username);


    /**
     * 获取用户DO
     * @param username 用户名
     * @return 用户DO
     */
    SysUserDO findSysUserDo(String username);


    /**
     * 修改用户自己的密码
     * @param originPassword 原密码
     * @param newPassword 新密码
     * @return 操作结果
     */
    Integer changePassWord(String originPassword, String newPassword);


    /**
     * 保存用户角色的关联关系
     * @param userId 角色id
     * @param roleIds  权限id
     * @return 保存数量
     */
    Integer saveUserRoleRelation(Long userId, List<Long> roleIds);

}
