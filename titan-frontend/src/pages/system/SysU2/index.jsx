import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React from 'react';
import SysU2Table from './components/SysU2Table';
import { connect } from 'dva';

const SysU2Page = ({ systemSysU2 }) => {
  return (
    <PageHeaderWrapper>
      <SysU2Table />
    </PageHeaderWrapper>
  );
};

export default connect(({ systemSysU2 }) => ({
  systemSysU2,
}))(SysU2Page);
