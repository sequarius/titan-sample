import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React, { useState, useEffect } from 'react';
import UserTable from './components/UserTable';
import { connect } from 'dva';

const UserPage = ({ systemUser }) => {
  return (
    <PageHeaderWrapper>
      <UserTable />
    </PageHeaderWrapper>
  );
};

export default connect(({ systemUser }) => ({
  systemUser,
}))(UserPage);
