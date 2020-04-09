package com.sequarius.titan.sample.system.service;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.domain.SysMetadataDO;
import com.sequarius.titan.sample.system.domain.MetaDataSelectResponseDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataRequestDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataResponseDTO;

import java.util.List;

/**
 * 元数据 服务
 *
 * @author titan-generator
 * @since 2020-03-20
 */
public interface SysMetadataService {

    /**
     * 获取元数据列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @param groupId
     * @return 元数据列表
     */
    PageData<SysMetadataResponseDTO> listSysMetadatas(Page page, String keyword, Long groupId);

    /**
     * 获取元数据选项表
     *
     * @param keyword 搜索关键字
     * @param groupKey 数据集key
     * @return 元数据列表
     */
    MetaDataSelectResponseDTO listSysMetadatas(String keyword, String groupKey);

    /**
     * 新增元数据
     *
     * @param requestDTO 元数据请求实体
     * @return 操作成功数量
     */
    Integer saveSysMetadata(SysMetadataRequestDTO requestDTO);

    /**
     * 更新元数据
     *
     * @param requestDTO 元数据请求实体
     * @return 操作成功数量
     */
    Integer updateSysMetadata(SysMetadataRequestDTO requestDTO);

    /**
     * 获取元数据
     *
     * @param id 元数据id
     * @return 元数据响应实体
     */
    SysMetadataResponseDTO findSysMetadata(Long id);

    /**
     * 根据组ID和编码获取对应的元数据
     * @param groupId 组Id
     * @param groupNO 编码
     * @return 元数据DO
     */
    SysMetadataDO findMetadata(Long groupId, String groupNO);

    /**
     * 删除元数据
     *
     * @param ids 删除元数据id列表
     * @return 删除成功数量
     */
    Integer removeSysMetadata(List<Long> ids);
}
