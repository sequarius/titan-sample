package com.sequarius.titan.sample.system.service;

import com.sequarius.titan.sample.common.Page;
import com.sequarius.titan.sample.common.PageData;
import com.sequarius.titan.sample.system.domain.SysU2RequestDTO;
import com.sequarius.titan.sample.system.domain.SysU2ResponseDTO;

import java.util.List;

/**
 * 用户22 服务
 *
 * @author titan-generator
 * @since 2020-02-23
 */
public interface SysU2Service {

    /**
     * 获取用户22列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 用户22列表
     */
    PageData<SysU2ResponseDTO> listSysU2s(Page page, String keyword);

    /**
     * 新增用户22
     *
     * @param requestDTO 用户22请求实体
     * @return 操作成功数量
     */
    Integer saveSysU2(SysU2RequestDTO requestDTO);

    /**
     * 获取用户22
     *
     * @param id 用户22id
     * @return 用户22响应实体
     */
    SysU2ResponseDTO findSysU2(Long id);
}
