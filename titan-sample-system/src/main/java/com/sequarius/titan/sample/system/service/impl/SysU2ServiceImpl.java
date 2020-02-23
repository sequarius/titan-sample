package com.sequarius.titan.sample.system.service.impl;

import com.sequarius.titan.sample.domain.SysUserDO;
import com.sequarius.titan.sample.domain.SysUserDOExample;
import com.sequarius.titan.sample.repository.SysUserDOMapper;
import com.sequarius.titan.sample.common.Page;
import com.sequarius.titan.sample.common.PageData;
import com.sequarius.titan.sample.system.domain.SysU2RequestDTO;
import com.sequarius.titan.sample.system.domain.SysU2ResponseDTO;
import com.sequarius.titan.sample.system.service.SysU2Service;
import com.sequarius.titan.sample.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户22 服务基础实现
 *
 * @author titan-generator
 * @since 2020-02-23
 */
@Service
@Slf4j
public class SysU2ServiceImpl implements SysU2Service {

    @Resource
    private SysUserDOMapper sysU2Mapper;

    /**
     * 获取用户22列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 用户22列表
     */
    @Override
    public PageData<SysU2ResponseDTO> listSysU2s(Page page, String keyword) {
        SysUserDOExample example = new SysUserDOExample();
        example.setPage(page);
        if (!StringUtils.isEmpty(keyword)) {
            example.or().andDeletedEqualTo(false).andPhoneNumberLike(keyword + "%");
            example.or().andDeletedEqualTo(false).andUsernameLike(keyword + "%");
        }else{
            example.createCriteria().andDeletedEqualTo(false);
        }
        List<SysU2ResponseDTO> data = BeanUtils.copyList(sysU2Mapper.selectByExample(example), SysU2ResponseDTO.class);
        long totalCount = sysU2Mapper.countByExample(example);
        return new PageData<>(data,totalCount,page);
    }

    /**
     * 新增用户22
     *
     * @param requestDTO 用户22请求实体
     * @return 操作成功数量
     */
    @Override
    public Integer saveSysU2(SysU2RequestDTO requestDTO) {
        SysUserDO sysU2DO = new SysUserDO();
        BeanUtils.copyProperties(requestDTO, sysU2DO);
        return sysU2Mapper.insertSelective(sysU2DO);
    }

    /**
     * 获取用户22
     *
     * @param id 用户22id
     * @return 用户22响应实体
     */
    @Override
    public SysU2ResponseDTO findSysU2(Long id) {
        SysUserDO sysU2DO = findSysUserDOById(id);
        if (sysU2DO == null) {
            return null;
        }
        SysU2ResponseDTO sysU2DTO = new SysU2ResponseDTO();
        BeanUtils.copyProperties(sysU2DO, sysU2DTO);
        return sysU2DTO;
    }

    /**
     * 通过id获取DO
     *
     * @param id 用户22id
     * @return 用户22响应实体
     */
    private SysUserDO findSysUserDOById(Long id) {
        SysUserDOExample example = new SysUserDOExample();
        example.createCriteria().andDeletedEqualTo(false).andIdEqualTo(id);
        List<SysUserDO> sysU2s = sysU2Mapper.selectByExample(example);
        if (sysU2s .size() != 1) {
            log.warn("try to find {} with id {}, but find {}","SysUserDO",id,sysU2s);
            return null;
        }
        return sysU2s.get(0);
     }
}
