package com.sequarius.titan.sample.system.service;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.system.domain.SysConfigRequestDTO;
import com.sequarius.titan.sample.system.domain.SysConfigResponseDTO;

import java.util.List;

/**
 * 系统配置 服务
 *
 * @author titan-generator
 * @since 2020-03-19
 */
public interface SysConfigService {

    /**
     * 获取系统配置列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 系统配置列表
     */
    PageData<SysConfigResponseDTO> listSysConfigs(Page page, String keyword);

    /**
     * 新增系统配置
     *
     * @param requestDTO 系统配置请求实体
     * @return 操作成功数量
     */
    Integer saveSysConfig(SysConfigRequestDTO requestDTO);

    /**
     * 更新系统配置
     *
     * @param requestDTO 系统配置请求实体
     * @return 操作成功数量
     */
    Integer updateSysConfig(SysConfigRequestDTO requestDTO);

    /**
     * 获取系统配置
     *
     * @param id 系统配置id
     * @return 系统配置响应实体
     */
    SysConfigResponseDTO findSysConfig(Long id);

    /**
     * 删除系统配置
     *
     * @param ids 删除系统配置id列表
     * @return 删除成功数量
     */
    Integer removeSysConfig(List<Long> ids);

    /**
     * 通过配置键获取配置
     * @param key key
     * @return 配置值
     */
    String getSysConfig(String key);
}
