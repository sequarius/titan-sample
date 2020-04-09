package com.sequarius.titan.sample.system.service;

import com.sequarius.titan.sample.domain.SysMetadataGroupDO;
import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.system.domain.SysMetadataGroupRequestDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataGroupResponseDTO;

import java.util.List;

/**
 * 元数据集 服务
 *
 * @author titan-generator
 * @since 2020-03-20
 */
public interface SysMetadataGroupService {

    /**
     * 获取元数据集列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 元数据集列表
     */
    PageData<SysMetadataGroupResponseDTO> listSysMetadataGroups(Page page, String keyword);

    /**
     * 新增元数据集
     *
     * @param requestDTO 元数据集请求实体
     * @return 操作成功数量
     */
    Integer saveSysMetadataGroup(SysMetadataGroupRequestDTO requestDTO);

    /**
     * 更新元数据集
     *
     * @param requestDTO 元数据集请求实体
     * @return 操作成功数量
     */
    Integer updateSysMetadataGroup(SysMetadataGroupRequestDTO requestDTO);

    /**
     * 获取元数据集
     *
     * @param id 元数据集id
     * @return 元数据集响应实体
     */
    SysMetadataGroupResponseDTO findSysMetadataGroup(Long id);

    /**
     * 获取元数据集
     *
     * @param key 元数据集键
     * @return 元数据集响应实体DO
     */
    SysMetadataGroupDO findSysMetadataGroup(String key);

    /**
     * 删除元数据集
     *
     * @param ids 删除元数据集id列表
     * @return 删除成功数量
     */
    Integer removeSysMetadataGroup(List<Long> ids);
}
