import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React from 'react';
import SysConfigTable from './components/SysConfigTable';
import { connect } from 'dva';

const SysConfigPage = ({ systemSysConfig }) => {
    return (
        <PageHeaderWrapper
        >
            <SysConfigTable />
        </PageHeaderWrapper>
    );
};

export default connect(({ systemSysConfig }) => ({
    systemSysConfig,
}))(SysConfigPage);
