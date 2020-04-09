package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.util.BeanUtils;
import com.sequarius.titan.sample.domain.SysMetadataDO;
import com.sequarius.titan.sample.domain.SysMetadataDOExample;
import com.sequarius.titan.sample.domain.SysMetadataGroupDO;
import com.sequarius.titan.sample.repository.SysMetadataDOMapper;
import com.sequarius.titan.sample.system.domain.MetaDataSelectResponseDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataRequestDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataResponseDTO;
import com.sequarius.titan.sample.system.repository.MetaDataRepository;
import com.sequarius.titan.sample.system.service.SysMetadataGroupService;
import com.sequarius.titan.sample.system.service.SysMetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 元数据 服务基础实现
 *
 * @author titan-generator
 * @since 2020-03-20
 */
@Service
@Slf4j
public class
SysMetadataServiceImpl implements SysMetadataService {

    @Resource
    private SysMetadataDOMapper sysMetadataMapper;

    @Resource
    private MetaDataRepository metaDataRepository;

    @Resource
    private SysMetadataGroupService sysMetadataGroupService;

    /**
     * 获取元数据列表
     *
     * @param page    分页参数
     * @param keyword 搜索关键字
     * @param groupId
     * @return 元数据列表
     */
    @Override
    public PageData<SysMetadataResponseDTO> listSysMetadatas(Page page, String keyword, Long groupId) {
        SysMetadataDOExample example = new SysMetadataDOExample();
        example.setPage(page);
        example.setOrderByClause("id DESC");
        SysMetadataDOExample.Criteria criteria = example.createCriteria().andDeletedEqualTo(false);
        if (groupId != null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andLabelLike(keyword + "%");
        }
        List<SysMetadataResponseDTO> data = BeanUtils.copyList(sysMetadataMapper.selectByExample(example), SysMetadataResponseDTO.class);
        long totalCount = sysMetadataMapper.countByExample(example);
        return new PageData<>(data, totalCount, page);
    }

    @Override
    public MetaDataSelectResponseDTO listSysMetadatas(String keyword, String groupKey) {
        MetaDataSelectResponseDTO responseDTO = new MetaDataSelectResponseDTO();
        SysMetadataGroupDO sysMetadataGroup = sysMetadataGroupService.findSysMetadataGroup(groupKey);
        if (sysMetadataGroup == null) {
            return null;
        }
        BeanUtils.copyPropertiesIgnoreNull(sysMetadataGroup, responseDTO);

        responseDTO.setMetadatas(BeanUtils.copyListIgnoreNull(
                metaDataRepository.listByGroupKey(keyword, groupKey),SysMetadataResponseDTO.class));
        return responseDTO;
    }

    /**
     * 新增元数据
     *
     * @param requestDTO 元数据请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer saveSysMetadata(SysMetadataRequestDTO requestDTO) {
        if (requestDTO.getGroupNo() == null) {
            requestDTO.setGroupNo(String.valueOf(metaDataRepository.getNextGroupNo(requestDTO.getGroupId())));
        }
        SysMetadataDO sysMetadataDO = new SysMetadataDO();
        BeanUtils.copyProperties(requestDTO, sysMetadataDO);
        return sysMetadataMapper.insertSelective(sysMetadataDO);
    }

    /**
     * 更新元数据
     *
     * @param requestDTO 元数据请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer updateSysMetadata(SysMetadataRequestDTO requestDTO) {
        SysMetadataDO sysMetadataDO = findSysMetadataDOById(requestDTO.getId());
        if (sysMetadataDO == null) {
            return -1;
        }
        BeanUtils.copyPropertiesIgnoreNull(requestDTO, sysMetadataDO);
        return sysMetadataMapper.updateByPrimaryKeySelective(sysMetadataDO);
    }

    /**
     * 获取元数据
     *
     * @param id 元数据id
     * @return 元数据响应实体
     */
    @Override
    public SysMetadataResponseDTO findSysMetadata(Long id) {
        SysMetadataDO sysMetadataDO = findSysMetadataDOById(id);
        if (sysMetadataDO == null) {
            return null;
        }
        SysMetadataResponseDTO sysMetadataDTO = new SysMetadataResponseDTO();
        BeanUtils.copyProperties(sysMetadataDO, sysMetadataDTO);
        return sysMetadataDTO;
    }

    @Override
    public SysMetadataDO findMetadata(Long groupId, String groupNO) {
        SysMetadataDOExample example = new SysMetadataDOExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andGroupNoEqualTo(groupNO).andDeletedEqualTo(false);
        List<SysMetadataDO> sysMetadataDOS = sysMetadataMapper.selectByExample(example);
        if (sysMetadataDOS.size() != 1) {
            return null;
        }
        return sysMetadataDOS.get(0);
    }

    /**
     * 删除元数据
     *
     * @param ids 删除元数据id列表
     * @return 删除成功数量
     */
    @Override
    public Integer removeSysMetadata(List<Long> ids) {
        SysMetadataDO sysMetadata = new SysMetadataDO();
        sysMetadata.setDeleted(true);
        SysMetadataDOExample example = new SysMetadataDOExample();
        example.createCriteria().andIdIn(ids).andDeletedEqualTo(false);
        return sysMetadataMapper.updateByExampleSelective(sysMetadata, example);
    }

    /**
     * 通过id获取DO
     *
     * @param id 元数据id
     * @return 元数据响应实体
     */
    private SysMetadataDO findSysMetadataDOById(Long id) {
        SysMetadataDOExample example = new SysMetadataDOExample();
        example.createCriteria().andDeletedEqualTo(false).andIdEqualTo(id);
        List<SysMetadataDO> sysMetadatas = sysMetadataMapper.selectByExample(example);
        if (sysMetadatas.size() != 1) {
            log.warn("try to find {} with id {}, but find {}", "SysMetadataDO", id, sysMetadatas);
            return null;
        }
        return sysMetadatas.get(0);
    }
}
