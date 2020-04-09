import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React from 'react';
import SysMetadataGroupTable from './components/SysMetadataGroupTable';
import { connect } from 'dva';

const SysMetadataGroupPage = ({ systemSysMetadataGroup }) => {
    return (
        <PageHeaderWrapper
        >
            <SysMetadataGroupTable />
        </PageHeaderWrapper>
    );
};

export default connect(({ systemSysMetadataGroup }) => ({
    systemSysMetadataGroup,
}))(SysMetadataGroupPage);
