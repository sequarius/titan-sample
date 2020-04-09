package com.sequarius.titan.sample.repository;

import com.sequarius.titan.sample.domain.SysUserRoleDO;
import com.sequarius.titan.sample.domain.SysUserRoleDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    long countByExample(SysUserRoleDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int deleteByExample(SysUserRoleDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int insert(SysUserRoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int insertSelective(SysUserRoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    List<SysUserRoleDO> selectByExample(SysUserRoleDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    SysUserRoleDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByExampleSelective(@Param("record") SysUserRoleDO record, @Param("example") SysUserRoleDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByExample(@Param("record") SysUserRoleDO record, @Param("example") SysUserRoleDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByPrimaryKeySelective(SysUserRoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Apr 09 16:20:27 CST 2020
     */
    int updateByPrimaryKey(SysUserRoleDO record);
}