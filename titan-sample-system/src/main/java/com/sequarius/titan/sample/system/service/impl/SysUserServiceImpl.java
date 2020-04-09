package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.titan.sample.common.CurrentUser;
import com.sequarius.titan.sample.common.domain.Entry;
import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.util.BeanUtils;
import com.sequarius.titan.sample.domain.*;
import com.sequarius.titan.sample.repository.SysUserDOMapper;
import com.sequarius.titan.sample.repository.SysUserRoleDOMapper;
import com.sequarius.titan.sample.system.domain.SysUserRequestDTO;
import com.sequarius.titan.sample.system.domain.SysUserResponseDTO;
import com.sequarius.titan.sample.system.repository.UserRolePermissionRepository;
import com.sequarius.titan.sample.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 用户 服务基础实现
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDOMapper sysUserMapper;

    private PasswordService passwordService;

    @Resource
    private SysUserRoleDOMapper sysUserRoleDOMapper;

    @Resource
    private UserRolePermissionRepository userRolePermissionRepository;


    @PostConstruct
    private void init(){
        this.passwordService = new DefaultPasswordService();
    }
    /**
     * 获取用户列表
     *
     * @param page    分页参数
     * @param keyword 搜索关键字
     * @return 用户列表
     */
    @Override
    public PageData<SysUserResponseDTO> listSysUsers(Page page, String keyword, Set<Long> ids) {
        SysUserDOExample example = new SysUserDOExample();
        example.setPage(page);
        example.setOrderByClause("id DESC");
        if (ids.size()>0){
            example.createCriteria().andDeletedEqualTo(false).andIdIn(new ArrayList<>(ids));
        }else if (!StringUtils.isEmpty(keyword)) {
            example.or().andDeletedEqualTo(false).andUsernameLike(keyword + "%");
        } else {
            example.createCriteria().andDeletedEqualTo(false);
        }
        List<SysUserResponseDTO> data = BeanUtils.copyList(sysUserMapper.selectByExample(example), SysUserResponseDTO.class);

        for (SysUserResponseDTO userDTO : data) {
            LinkedList<Entry<String, String>> entries = new LinkedList<>();
            List<SysRoleDO> sysRoleDOS = userRolePermissionRepository.listRoles(userDTO.getId());
            for (SysRoleDO sysRoleDO : sysRoleDOS) {
                entries.add(new Entry<>(String.valueOf(sysRoleDO.getId()),sysRoleDO.getDescription()));
            }
            userDTO.setRoles(entries);
        }

        long totalCount = sysUserMapper.countByExample(example);
        return new PageData<>(data, totalCount, page);
    }

    /**
     * 新增用户
     *
     * @param requestDTO 用户请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer saveSysUser(SysUserRequestDTO requestDTO) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(requestDTO, sysUserDO);
        sysUserDO.setPassword(passwordService.encryptPassword(sysUserDO.getPassword()));
        int result = sysUserMapper.insertSelective(sysUserDO);
        this.saveUserRoleRelation(sysUserDO.getId(),requestDTO.getRoleIds());
        requestDTO.setId(sysUserDO.getId());
        return result;
    }

    /**
     * 更新用户
     *
     * @param requestDTO 用户请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer updateSysUser(SysUserRequestDTO requestDTO) {
        SysUserDO sysUserDO = findSysUserDOById(requestDTO.getId());
        if (sysUserDO == null) {
            return -1;
        }
        if (!StringUtils.isEmpty(requestDTO.getPassword())) {
            requestDTO.setPassword(passwordService.encryptPassword(requestDTO.getPassword()));
        }
        BeanUtils.copyPropertiesIgnoreNull(requestDTO, sysUserDO);
        int result = sysUserMapper.updateByPrimaryKeySelective(sysUserDO);
        this.saveUserRoleRelation(sysUserDO.getId(),requestDTO.getRoleIds());
        return result;
    }

    /**
     * 获取用户
     *
     * @param id 用户id
     * @return 用户响应实体
     */
    @Override
    public SysUserResponseDTO findSysUser(Long id) {
        SysUserDO sysUserDO = findSysUserDOById(id);
        if (sysUserDO == null) {
            return null;
        }
        SysUserResponseDTO sysUserDTO = new SysUserResponseDTO();
        BeanUtils.copyProperties(sysUserDO, sysUserDTO);
        return sysUserDTO;
    }

    /**
     * 删除用户
     *
     * @param ids 删除用户id列表
     * @return 删除成功数量
     */
    @Override
    public Integer removeSysUser(List<Long> ids) {
        SysUserDO sysUser = new SysUserDO();
        sysUser.setDeleted(true);
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andIdIn(ids).andDeletedEqualTo(false);
        return sysUserMapper.updateByExampleSelective(sysUser, example);
    }

    @Override
    public SysUserResponseDTO findSysUser(String username) {
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andUsernameEqualTo(username.trim());
        List<SysUserDO> sysUserDOS = sysUserMapper.selectByExample(example);
        if (sysUserDOS.size() != 1) {
            return null;
        }
        SysUserResponseDTO responseDTO = new SysUserResponseDTO();
        BeanUtils.copyPropertiesIgnoreNull(sysUserDOS.get(0), responseDTO);
        return responseDTO;
    }

    @Override
    public SysUserDO findSysUserDo(String username) {
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andUsernameEqualTo(username).andDeletedEqualTo(false);
        List<SysUserDO> sysUserDOS = sysUserMapper.selectByExample(example);

        if (sysUserDOS.size() != 1) {
            log.warn("find more than 1 user -> {} with username {}", sysUserDOS, username);
            return null;
        }
        return sysUserDOS.get(0);
    }

    @Override
    public Integer changePassWord(String originPassword, String newPassword) {
        SysUserDO sysUserDO = this.findSysUserDOById(CurrentUser.getCurrentUser().getId());
        if(sysUserDO==null){
            return 2;
        }
        if(!passwordService.passwordsMatch(originPassword,sysUserDO.getPassword())){
            return 3;
        }
        if(passwordService.passwordsMatch(newPassword,sysUserDO.getPassword())){
            return 4;
        }
        sysUserDO.setPassword(passwordService.encryptPassword(newPassword));
        return sysUserMapper.updateByPrimaryKeySelective(sysUserDO);
    }

    @Override
    public Integer saveUserRoleRelation(Long userId, List<Long> roleIds) {
        int result = 0;

        // 删除原有角色权限分配
        SysUserRoleDOExample example = new SysUserRoleDOExample();
        example.createCriteria().andUserIdEqualTo(userId);
        sysUserRoleDOMapper.deleteByExample(example);

        if (roleIds==null){
            return result;
        }
        // 插入关联表
        for (Long roleId : roleIds) {
            SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
            sysUserRoleDO.setUserId(userId);
            sysUserRoleDO.setRoleId(roleId);
            result += sysUserRoleDOMapper.insertSelective(sysUserRoleDO);
        }

        return result;
    }

    /**
     * 通过id获取DO
     *
     * @param id 用户id
     * @return 用户响应实体
     */
    private SysUserDO findSysUserDOById(Long id) {
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andDeletedEqualTo(false).andIdEqualTo(id);
        List<SysUserDO> sysUsers = sysUserMapper.selectByExample(example);
        if (sysUsers.size() != 1) {
            log.warn("try to find {} with id {}, but find {}", "SysUserDO", id, sysUsers);
            return null;
        }
        return sysUsers.get(0);
    }
}
