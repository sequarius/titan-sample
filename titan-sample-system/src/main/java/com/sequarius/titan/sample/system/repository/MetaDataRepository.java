package com.sequarius.titan.sample.system.repository;

import com.sequarius.titan.sample.domain.SysMetadataDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * project erp-bundle
 *
 * @author JiangDingMing *
 * @since 20/03/2020
 */
public interface MetaDataRepository {
    /**
     * 获取group id 对应的下一个ID
     * @param groupId 元数据组id
     * @return 下一个ID
     */
    Long getNextGroupNo(@Param("groupId") Long groupId);

    List<SysMetadataDO> listByGroupKey(@Param("keyword") String keyword, @Param("groupKey") String groupKey);

    /**
     * 根据metadata id 获取label
     * @param id id
     * @return 格式化后的label
     */
    String getFormatMetadataLabel(@Param("id") Long id, @Param("includeTag") boolean includeTag);
}
