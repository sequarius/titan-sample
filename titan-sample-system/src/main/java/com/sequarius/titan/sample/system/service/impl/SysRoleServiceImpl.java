package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.titan.sample.common.domain.Entry;
import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.TreeNodeDTO;
import com.sequarius.titan.sample.common.util.BeanUtils;
import com.sequarius.titan.sample.domain.*;
import com.sequarius.titan.sample.repository.SysRoleDOMapper;
import com.sequarius.titan.sample.repository.SysRolePermissionDOMapper;
import com.sequarius.titan.sample.repository.SysUserRoleDOMapper;
import com.sequarius.titan.sample.system.domain.SysRoleRequestDTO;
import com.sequarius.titan.sample.system.domain.SysRoleResponseDTO;
import com.sequarius.titan.sample.system.repository.UserRolePermissionRepository;
import com.sequarius.titan.sample.system.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 角色 服务基础实现
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDOMapper sysRoleMapper;

    @Resource
    private UserRolePermissionRepository userRolePermissionRepository;

    @Resource
    private SysRolePermissionDOMapper rolePermissionDOMapper;

    @Resource
    private SysUserRoleDOMapper sysUserRoleDOMapper;



    /**
     * 获取角色列表
     *
     * @param page    分页参数
     * @param keyword 搜索关键字
     * @return 角色列表
     */
    @Override
    public PageData<SysRoleResponseDTO> listSysRoles(Page page, String keyword) {
        SysRoleDOExample example = new SysRoleDOExample();
        example.setPage(page);
        example.setOrderByClause("id DESC");
        List<SysRoleResponseDTO> roleDOs = BeanUtils.copyList(sysRoleMapper.selectByExample(example), SysRoleResponseDTO.class);
        for (SysRoleResponseDTO roleDO : roleDOs) {
            LinkedList<Entry<String, String>> entries = new LinkedList<>();
            List<SysPermissionDO> permissionDOs = userRolePermissionRepository.listPermissions(roleDO.getId());;
            for (SysPermissionDO permissionDO : permissionDOs) {
                entries.add(new Entry<>(String.valueOf(permissionDO.getId()), permissionDO.getDescription()));
            }
            roleDO.setPermissions(entries);
        }
        long totalCount = sysRoleMapper.countByExample(example);
        return new PageData<>(roleDOs, totalCount, page);
    }

    /**
     * 新增角色
     *
     * @param requestDTO 角色请求实体
     * @return 操作成功数量
     */
    @Override
    @Transactional
    public Integer saveSysRole(SysRoleRequestDTO requestDTO) {
        SysRoleDO sysRoleDO = new SysRoleDO();
        BeanUtils.copyProperties(requestDTO, sysRoleDO);
        int result = sysRoleMapper.insertSelective(sysRoleDO);
        this.saveRolePermissionRelation(sysRoleDO.getId(), requestDTO.getPermissionIds());
        return result;
    }

    /**
     * 更新角色
     *
     * @param requestDTO 角色请求实体
     * @return 操作成功数量
     */
    @Override
    @Transactional
    public Integer updateSysRole(SysRoleRequestDTO requestDTO) {
        SysRoleDO sysRoleDO = findSysRoleDOById(requestDTO.getId());
        if (sysRoleDO == null) {
            return -1;
        }
        BeanUtils.copyProperties(requestDTO, sysRoleDO);
        int result = sysRoleMapper.updateByPrimaryKey(sysRoleDO);
        this.saveRolePermissionRelation(sysRoleDO.getId(), requestDTO.getPermissionIds());
        return result;
    }

    /**
     * 获取角色
     *
     * @param id 角色id
     * @return 角色响应实体
     */
    @Override
    public SysRoleResponseDTO findSysRole(Long id) {
        SysRoleDO sysRoleDO = findSysRoleDOById(id);
        if (sysRoleDO == null) {
            return null;
        }
        SysRoleResponseDTO sysRoleDTO = new SysRoleResponseDTO();
        BeanUtils.copyProperties(sysRoleDO, sysRoleDTO);
        return sysRoleDTO;
    }

    /**
     * 删除角色
     *
     * @param ids 删除角色id列表
     * @return 删除成功数量
     */
    @Override
    @Transactional
    public Integer removeSysRole(List<Long> ids) {
        // 删除对应的用户关联关系
        SysUserRoleDOExample userRoleDOExample = new SysUserRoleDOExample();
        userRoleDOExample.createCriteria().andRoleIdIn(ids);
        sysUserRoleDOMapper.deleteByExample(userRoleDOExample);

        // 删除对应的权限关联关系
        SysRolePermissionDOExample rolePermissionDOExample = new SysRolePermissionDOExample();
        rolePermissionDOExample.createCriteria().andRoleIdIn(ids);
        rolePermissionDOMapper.deleteByExample(rolePermissionDOExample);

        // 删除角色实体
        SysRoleDOExample example = new SysRoleDOExample();
        example.createCriteria().andIdIn(ids);
        return sysRoleMapper.deleteByExample(example);
    }

    @Override
    public SysRoleDO findSysRoleDo(String role) {
        SysRoleDOExample example = new SysRoleDOExample();
        example.createCriteria().andRoleEqualTo(role);
        List<SysRoleDO> sysRoleDOS = sysRoleMapper.selectByExample(example);
        if (sysRoleDOS.size() != 1) {
            return null;
        }
        return sysRoleDOS.get(0);
    }

    @Override
    public SysRoleDO findSysRoleDo(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TreeNodeDTO> roleTree(String keyword) {
        SysRoleDOExample example = new SysRoleDOExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.or().andRoleLike("%" + keyword);
            example.or().andDescriptionLike("%" + keyword);
        }
        List<SysRoleDO> sysRoleDOS = sysRoleMapper.selectByExample(example);
        List<TreeNodeDTO> nodeDTOS = new LinkedList<>();
        for (SysRoleDO sysRoleDO : sysRoleDOS) {
            TreeNodeDTO treeNodeDTO = new TreeNodeDTO();
            treeNodeDTO.setTitle(sysRoleDO.getDescription());
            treeNodeDTO.setValue(String.valueOf(sysRoleDO.getId()));
            treeNodeDTO.setKey(String.valueOf(sysRoleDO.getId()));
            nodeDTOS.add(treeNodeDTO);
        }
        return nodeDTOS;
    }

    @Override
    public String findCircleDependency(SysRoleRequestDTO requestDTO) {
        SysRoleDO sysRoleDO = new SysRoleDO();
        BeanUtils.copyProperties(requestDTO,sysRoleDO);
        if (requestDTO.getParentId() == null) {
            return null;
        }
        Map<Long, String> parentMap = new LinkedHashMap<>();
        do {
            if (parentMap.containsKey(sysRoleDO.getId())) {
                LinkedList<String> parentNames = new LinkedList<>(parentMap.values());
                parentNames.add(sysRoleDO.getDescription());
                return String.join(" → ", parentNames);
            }
            parentMap.put(sysRoleDO.getId(), sysRoleDO.getDescription());
            sysRoleDO = findSysRoleDOById(sysRoleDO.getParentId());
        } while (sysRoleDO.getParentId() != null);
        return null;
    }

    @Override
    public Integer saveRolePermissionRelation(Long roleId, List<Long> permissionIds) {

        int result = 0;

        // 删除原有角色权限分配
        SysRolePermissionDOExample example = new SysRolePermissionDOExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionDOMapper.deleteByExample(example);

        // 插入关联表
        for (Long permissionId : permissionIds) {
            SysRolePermissionDO sysRolePermissionDO = new SysRolePermissionDO();
            sysRolePermissionDO.setPermissionId(permissionId);
            sysRolePermissionDO.setRoleId(roleId);
            result += rolePermissionDOMapper.insertSelective(sysRolePermissionDO);
        }

        return result;
    }

    /**
     * 通过id获取DO
     *
     * @param id 角色id
     * @return 角色响应实体
     */
    private SysRoleDO findSysRoleDOById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }
}
