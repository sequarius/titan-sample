package com.sequarius.sample.system.api.service;

import com.sequarius.sample.system.api.domain.UserBO;

import java.util.List;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
public interface SystemService {
    /**
     * 通过用户名找到UserBO
     * @param username 用户名
     * @return UserBO
     */
    UserBO findUser(String username);

    /**
     * 创建一个UserBO
     * @param userBO userBO实体
     * @return 创建数量 -1为用户名已存在
     */
    Integer saveUser(UserBO userBO);

    /**
     * 创建一个删除一个用户
     * @param ids 用户id集合
     * @return 删除数量
     */
    Integer removeUser(List<Long> ids);

    /**
     * 根据ID查询元数据label
     * @param id 元数据id
     * @return label
     */
    String getFormatMetadataLabel(Long id);

    /**
     * 根据元数据查询数据label
     * @param id 元数据id
     * @param includeTag  是否包含tag，默认为false
     * @return 元数据label
     */
    String getFormatMetadataLabel(Long id,boolean includeTag);


}
