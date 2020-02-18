import React from 'react';
import { Divider, Popconfirm } from 'antd';
import ProTable from '@ant-design/pro-table';
import styles from './index.less';
import UserModal from '../UserModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const DefaultTable = ({ dispatch, systemUser }) => {
  const columns = [
    {
      title: 'id',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: '用户名',
      dataIndex: 'username',
      key: 'username',
      render: text => <a>{text}</a>,
    },
    {
      title: '电话号码',
      dataIndex: 'phoneNumber',
      key: 'phoneNumber',
    },
    {
      title: '操作',
      key: 'id',
      render: (text, record) => (
        <span>
          <a onClick={() => updateUserHandler(record)}>修改</a>
          <Divider type="vertical" />
          <Popconfirm title="确认删除?" onConfirm={() => removeUserHandler(record.id)}>
            <a>删除</a>
          </Popconfirm>
        </span>
      ),
    },
  ];

  function removeUserHandler(id) {
    dispatch({
      type: 'systemUser/removeUser',
      payload: { id },
    });
  }

  function updateUserHandler(record) {
    dispatch({
      type: 'systemUser/setUser',
      payload: { user: record },
    });
  }

  function pageChangedHandler(page) {
    console.log(page);
    router.push({
      pathname: '/system/users',
      query: { page },
    });
  }

  return (
    <div className={styles.container}>
      <div id="components-table-demo-basic">
        <ProTable
          pagination={false}
          total={systemUser.total}
          toolBarRender={(action, { selectedRows }) => [<UserModal />]}
          headerTitle="用户列表"
          search={false}
          columns={columns}
          dataSource={systemUser.list}
        />
        <Pagination
          className="ant-table-pagination"
          onChange={pageChangedHandler}
          defaultCurrent={6}
          total={systemUser.total}
          current={systemUser.current}
        />
      </div>
    </div>
  );
};
export default connect(({ systemUser }) => ({
  systemUser,
}))(DefaultTable);
