import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React from 'react';
import SysMetadataTable from './components/SysMetadataTable';
import { connect } from 'dva';

const SysMetadataPage = ({ systemSysMetadata }) => {
    return (
        <PageHeaderWrapper
        >
            <SysMetadataTable />
        </PageHeaderWrapper>
    );
};

export default connect(({ systemSysMetadata }) => ({
    systemSysMetadata,
}))(SysMetadataPage);
