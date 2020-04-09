package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.util.BeanUtils;
import com.sequarius.titan.sample.domain.SysMetadataGroupDO;
import com.sequarius.titan.sample.domain.SysMetadataGroupDOExample;
import com.sequarius.titan.sample.repository.SysMetadataGroupDOMapper;
import com.sequarius.titan.sample.system.domain.SysMetadataGroupRequestDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataGroupResponseDTO;
import com.sequarius.titan.sample.system.service.SysMetadataGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 元数据集 服务基础实现
 *
 * @author titan-generator
 * @since 2020-03-20
 */
@Service
@Slf4j
public class SysMetadataGroupServiceImpl implements SysMetadataGroupService {

    @Resource
    private SysMetadataGroupDOMapper sysMetadataGroupMapper;

    /**
     * 获取元数据集列表
     *
     * @param page    分页参数
     * @param keyword 搜索关键字
     * @return 元数据集列表
     */
    @Override
    public PageData<SysMetadataGroupResponseDTO> listSysMetadataGroups(Page page, String keyword) {
        SysMetadataGroupDOExample example = new SysMetadataGroupDOExample();
        example.createCriteria().andDeletedEqualTo(false);
        example.setPage(page);
        example.setOrderByClause("id DESC");
        List<SysMetadataGroupResponseDTO> data = BeanUtils.copyList(sysMetadataGroupMapper.selectByExample(example), SysMetadataGroupResponseDTO.class);
        long totalCount = sysMetadataGroupMapper.countByExample(example);
        return new PageData<>(data, totalCount, page);
    }

    /**
     * 新增元数据集
     *
     * @param requestDTO 元数据集请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer saveSysMetadataGroup(SysMetadataGroupRequestDTO requestDTO) {
        SysMetadataGroupDO sysMetadataGroupDO = new SysMetadataGroupDO();
        BeanUtils.copyProperties(requestDTO, sysMetadataGroupDO);
        return sysMetadataGroupMapper.insertSelective(sysMetadataGroupDO);
    }

    /**
     * 更新元数据集
     *
     * @param requestDTO 元数据集请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer updateSysMetadataGroup(SysMetadataGroupRequestDTO requestDTO) {
        SysMetadataGroupDO sysMetadataGroupDO = findSysMetadataGroupDOById(requestDTO.getId());
        if (sysMetadataGroupDO == null) {
            return -1;
        }
        BeanUtils.copyPropertiesIgnoreNull(requestDTO, sysMetadataGroupDO);
        return sysMetadataGroupMapper.updateByPrimaryKeySelective(sysMetadataGroupDO);
    }

    /**
     * 获取元数据集
     *
     * @param id 元数据集id
     * @return 元数据集响应实体
     */
    @Override
    public SysMetadataGroupResponseDTO findSysMetadataGroup(Long id) {
        SysMetadataGroupDO sysMetadataGroupDO = findSysMetadataGroupDOById(id);
        if (sysMetadataGroupDO == null) {
            return null;
        }
        SysMetadataGroupResponseDTO sysMetadataGroupDTO = new SysMetadataGroupResponseDTO();
        BeanUtils.copyProperties(sysMetadataGroupDO, sysMetadataGroupDTO);
        return sysMetadataGroupDTO;
    }

    @Override
    public SysMetadataGroupDO findSysMetadataGroup(String key) {
        SysMetadataGroupDOExample example = new SysMetadataGroupDOExample();
        example.createCriteria().andKeyEqualTo(key).andDeletedEqualTo(false);
        List<SysMetadataGroupDO> sysMetadataGroupDOS = sysMetadataGroupMapper.selectByExample(example);
        if (sysMetadataGroupDOS.size() != 1) {
            return null;
        }
        return sysMetadataGroupDOS.get(0);
    }

    /**
     * 删除元数据集
     *
     * @param ids 删除元数据集id列表
     * @return 删除成功数量
     */
    @Override
    public Integer removeSysMetadataGroup(List<Long> ids) {
        SysMetadataGroupDO sysMetadataGroup = new SysMetadataGroupDO();
        sysMetadataGroup.setDeleted(true);
        SysMetadataGroupDOExample example = new SysMetadataGroupDOExample();
        example.createCriteria().andIdIn(ids).andDeletedEqualTo(false);
        return sysMetadataGroupMapper.updateByExampleSelective(sysMetadataGroup, example);
    }

    /**
     * 通过id获取DO
     *
     * @param id 元数据集id
     * @return 元数据集响应实体
     */
    private SysMetadataGroupDO findSysMetadataGroupDOById(Long id) {
        SysMetadataGroupDOExample example = new SysMetadataGroupDOExample();
        example.createCriteria().andDeletedEqualTo(false).andIdEqualTo(id);
        List<SysMetadataGroupDO> sysMetadataGroups = sysMetadataGroupMapper.selectByExample(example);
        if (sysMetadataGroups.size() != 1) {
            log.warn("try to find {} with id {}, but find {}", "SysMetadataGroupDO", id, sysMetadataGroups);
            return null;
        }
        return sysMetadataGroups.get(0);
    }
}
