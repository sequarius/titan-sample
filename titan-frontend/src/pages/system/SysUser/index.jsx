import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React from 'react';
import SysUserTable from './components/SysUserTable';
import { connect } from 'dva';

const SysUserPage = ({ systemSysUser }) => {
    return (
        <PageHeaderWrapper
        >
            <SysUserTable />
        </PageHeaderWrapper>
    );
};

export default connect(({ systemSysUser }) => ({
    systemSysUser,
}))(SysUserPage);
