import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React from 'react';
import SysPermissionTable from './components/SysPermissionTable';
import { connect } from 'dva';

const SysPermissionPage = ({ systemSysPermission }) => {
    return (
        <PageHeaderWrapper
        >
            <SysPermissionTable />
        </PageHeaderWrapper>
    );
};

export default connect(({ systemSysPermission }) => ({
    systemSysPermission,
}))(SysPermissionPage);
