package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.TreeNodeDTO;
import com.sequarius.titan.sample.common.util.BeanUtils;
import com.sequarius.titan.sample.domain.SysPermissionDO;
import com.sequarius.titan.sample.domain.SysPermissionDOExample;
import com.sequarius.titan.sample.repository.SysPermissionDOMapper;
import com.sequarius.titan.sample.system.domain.SysPermissionRequestDTO;
import com.sequarius.titan.sample.system.domain.SysPermissionResponseDTO;
import com.sequarius.titan.sample.system.repository.UserRolePermissionRepository;
import com.sequarius.titan.sample.system.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限 服务基础实现
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@Service
@Slf4j
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionDOMapper sysPermissionMapper;


    @Resource
    private UserRolePermissionRepository userRolePermissionRepository;

    /**
     * 获取权限列表
     *
     * @param page    分页参数
     * @param keyword 搜索关键字
     * @return 权限列表
     */
    @Override
    public PageData<SysPermissionResponseDTO> listSysPermissions(Page page, String keyword) {
        SysPermissionDOExample example = new SysPermissionDOExample();
        example.setPage(page);
        example.setOrderByClause("id DESC");
        List<SysPermissionResponseDTO> data = BeanUtils.copyList(sysPermissionMapper.selectByExample(example), SysPermissionResponseDTO.class);
        long totalCount = sysPermissionMapper.countByExample(example);
        return new PageData<>(data, totalCount, page);
    }

    /**
     * 新增权限
     *
     * @param requestDTO 权限请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer saveSysPermission(SysPermissionRequestDTO requestDTO) {
        SysPermissionDO sysPermissionDO = new SysPermissionDO();
        BeanUtils.copyProperties(requestDTO, sysPermissionDO);
        return sysPermissionMapper.insertSelective(sysPermissionDO);
    }

    /**
     * 更新权限
     *
     * @param requestDTO 权限请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer updateSysPermission(SysPermissionRequestDTO requestDTO) {
        SysPermissionDO sysPermissionDO = findSysPermissionDOById(requestDTO.getId());
        if (sysPermissionDO == null) {
            return -1;
        }
        BeanUtils.copyPropertiesIgnoreNull(requestDTO, sysPermissionDO);
        return sysPermissionMapper.updateByPrimaryKeySelective(sysPermissionDO);
    }

    /**
     * 获取权限
     *
     * @param id 权限id
     * @return 权限响应实体
     */
    @Override
    public SysPermissionResponseDTO findSysPermission(Long id) {
        SysPermissionDO sysPermissionDO = findSysPermissionDOById(id);
        if (sysPermissionDO == null) {
            return null;
        }
        SysPermissionResponseDTO sysPermissionDTO = new SysPermissionResponseDTO();
        BeanUtils.copyProperties(sysPermissionDO, sysPermissionDTO);
        return sysPermissionDTO;
    }

    /**
     * 删除权限
     *
     * @param ids 删除权限id列表
     * @return 删除成功数量
     */
    @Override
    public Integer removeSysPermission(List<Long> ids) {
        SysPermissionDOExample example = new SysPermissionDOExample();
        example.createCriteria().andIdIn(ids);
        return sysPermissionMapper.deleteByExample(example);
    }

    @Override
    public SysPermissionDO findSysPermission(String permission) {
        SysPermissionDOExample example = new SysPermissionDOExample();
        example.createCriteria().andPermissionEqualTo(permission);
        List<SysPermissionDO> sysPermissionDOS = sysPermissionMapper.selectByExample(example);
        if (sysPermissionDOS.size() != 1) {
            return null;
        }
        return sysPermissionDOS.get(0);
    }

    @Override
    public Integer saveSysPermissionDO(SysPermissionDO permissionDO) {
        if (permissionDO.getId() == null) {
            return sysPermissionMapper.insertSelective(permissionDO);
        }
        return sysPermissionMapper.updateByPrimaryKeySelective(permissionDO);
    }

    @Override
    public List<TreeNodeDTO> getPermissionTree(String keyword) {
        SysPermissionDOExample example = new SysPermissionDOExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.or().andPermissionLike("%" + keyword);
            example.or().andDescriptionLike("%" + keyword);
        }
        List<SysPermissionDO> permissionDOs = sysPermissionMapper.selectByExample(example);
        Map<String, List<SysPermissionDO>> groupMap = permissionDOs.stream().collect(Collectors
                .groupingBy(SysPermissionDO::getGroup));

        List<TreeNodeDTO> resultTreeDTO = new LinkedList<>();
        for (Map.Entry<String, List<SysPermissionDO>> groupEntry : groupMap.entrySet()) {
            TreeNodeDTO treeNodeDTO = new TreeNodeDTO();
            treeNodeDTO.setTitle(groupEntry.getKey() + "权限");
            treeNodeDTO.setChildren(new LinkedList<>());
            List<String> parentKey = new LinkedList<>();
            for (SysPermissionDO sysPermissionDO : groupEntry.getValue()) {
                TreeNodeDTO innerNode = new TreeNodeDTO();
                innerNode.setTitle(sysPermissionDO.getDescription());
                innerNode.setKey(String.valueOf(sysPermissionDO.getId()));
                innerNode.setValue(String.valueOf(sysPermissionDO.getId()));
                treeNodeDTO.getChildren().add(innerNode);
                parentKey.add(String.valueOf(sysPermissionDO.getId()));
            }
            treeNodeDTO.setKey(String.join(",", parentKey));
            treeNodeDTO.setValue(String.join(",", parentKey));
            resultTreeDTO.add(treeNodeDTO);
        }

        return resultTreeDTO;
    }

    /**
     * 通过id获取DO
     *
     * @param id 权限id
     * @return 权限响应实体
     */
    private SysPermissionDO findSysPermissionDOById(Long id) {
        return sysPermissionMapper.selectByPrimaryKey(id);
    }
}
