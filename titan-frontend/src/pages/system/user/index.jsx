import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React, { useState, useEffect } from 'react';
import { Spin } from 'antd';
import DefualtTable from './components/DefualtTable';
import { connect } from 'dva';

const UserPage = ({ systemUser }) => {
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    setTimeout(() => {
      setLoading(false);
    }, 3000);
  }, []);
  return (
    <PageHeaderWrapper
    // className={styles.main}
    >
      <DefualtTable />
      <div
        style={{
          paddingTop: 100,
          textAlign: 'center',
        }}
      >
        <Spin spinning={loading} size="large"></Spin>
      </div>
    </PageHeaderWrapper>
  );
};

export default connect(({ systemUser }) => ({
  systemUser,
}))(UserPage);
