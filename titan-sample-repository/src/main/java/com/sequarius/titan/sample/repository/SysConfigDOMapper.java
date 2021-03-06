package com.sequarius.titan.sample.repository;

import com.sequarius.titan.sample.domain.SysConfigDO;
import com.sequarius.titan.sample.domain.SysConfigDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysConfigDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    long countByExample(SysConfigDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int deleteByExample(SysConfigDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int insert(SysConfigDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int insertSelective(SysConfigDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    List<SysConfigDO> selectByExampleWithBLOBs(SysConfigDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    List<SysConfigDO> selectByExample(SysConfigDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    SysConfigDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByExampleSelective(@Param("record") SysConfigDO record, @Param("example") SysConfigDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByExampleWithBLOBs(@Param("record") SysConfigDO record, @Param("example") SysConfigDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByExample(@Param("record") SysConfigDO record, @Param("example") SysConfigDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByPrimaryKeySelective(SysConfigDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(SysConfigDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_config
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByPrimaryKey(SysConfigDO record);
}