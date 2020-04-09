import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React from 'react';
import SysRoleTable from './components/SysRoleTable';
import { connect } from 'dva';

const SysRolePage = ({ systemSysRole }) => {
    return (
        <PageHeaderWrapper
        >
            <SysRoleTable />
        </PageHeaderWrapper>
    );
};

export default connect(({ systemSysRole }) => ({
    systemSysRole,
}))(SysRolePage);
