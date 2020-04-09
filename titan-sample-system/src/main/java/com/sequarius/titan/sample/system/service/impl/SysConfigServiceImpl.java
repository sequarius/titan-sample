package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.util.BeanUtils;
import com.sequarius.titan.sample.domain.SysConfigDO;
import com.sequarius.titan.sample.domain.SysConfigDOExample;
import com.sequarius.titan.sample.repository.SysConfigDOMapper;
import com.sequarius.titan.sample.system.domain.SysConfigRequestDTO;
import com.sequarius.titan.sample.system.domain.SysConfigResponseDTO;
import com.sequarius.titan.sample.system.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统配置 服务基础实现
 *
 * @author titan-generator
 * @since 2020-03-19
 */
@Service
@Slf4j
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private SysConfigDOMapper sysConfigMapper;

    /**
     * 获取系统配置列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 系统配置列表
     */
    @Override
    public PageData<SysConfigResponseDTO> listSysConfigs(Page page, String keyword) {
        SysConfigDOExample example = new SysConfigDOExample();
        example.setPage(page);
        example.setOrderByClause("id DESC");
        List<SysConfigResponseDTO> data = BeanUtils.copyList(sysConfigMapper.selectByExampleWithBLOBs(example), SysConfigResponseDTO.class);
        long totalCount = sysConfigMapper.countByExample(example);
        return new PageData<>(data,totalCount,page);
    }

    /**
     * 新增系统配置
     *
     * @param requestDTO 系统配置请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer saveSysConfig(SysConfigRequestDTO requestDTO) {
        SysConfigDO sysConfigDO = new SysConfigDO();
        BeanUtils.copyProperties(requestDTO, sysConfigDO);
        return sysConfigMapper.insertSelective(sysConfigDO);
    }

    /**
     * 更新系统配置
     *
     * @param requestDTO 系统配置请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer updateSysConfig(SysConfigRequestDTO requestDTO) {
        SysConfigDO sysConfigDO = findSysConfigDOById(requestDTO.getId());
        if (sysConfigDO == null) {
            return -1;
        }
        BeanUtils.copyPropertiesIgnoreNull(requestDTO, sysConfigDO);
        return sysConfigMapper.updateByPrimaryKeySelective(sysConfigDO);
    }

    /**
     * 获取系统配置
     *
     * @param id 系统配置id
     * @return 系统配置响应实体
     */
    @Override
    public SysConfigResponseDTO findSysConfig(Long id) {
        SysConfigDO sysConfigDO = findSysConfigDOById(id);
        if (sysConfigDO == null) {
            return null;
        }
        SysConfigResponseDTO sysConfigDTO = new SysConfigResponseDTO();
        BeanUtils.copyProperties(sysConfigDO, sysConfigDTO);
        return sysConfigDTO;
    }

    /**
     * 删除系统配置
     *
     * @param ids 删除系统配置id列表
     * @return 删除成功数量
     */
    @Override
    public Integer removeSysConfig(List<Long> ids) {
        SysConfigDOExample example = new SysConfigDOExample();
        example.createCriteria().andIdIn(ids);
        return sysConfigMapper.deleteByExample(example);
    }

    @Override
    public String getSysConfig(String key) {
        SysConfigDOExample example = new SysConfigDOExample();
        example.createCriteria().andKeyEqualTo(key.trim()).andPublicConfigEqualTo(true);
        List<SysConfigDO> sysConfigDOS = sysConfigMapper.selectByExampleWithBLOBs(example);
        if(sysConfigDOS.size()!=1){
            return null;
        }
        return sysConfigDOS.get(0).getValue();
    }

    /**
     * 通过id获取DO
     *
     * @param id 系统配置id
     * @return 系统配置响应实体
     */
    private SysConfigDO findSysConfigDOById(Long id) {
        return sysConfigMapper.selectByPrimaryKey(id);
     }
}
