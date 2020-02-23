import React, { useRef, useEffect } from 'react';
import { Divider, Popconfirm, Button } from 'antd';
import ProTable from '@ant-design/pro-table';
import UserModal from '../UserModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const UserTable = ({ dispatch, systemUser, loading }) => {
  const isLoading = loading.effects['systemUser/list'];

  const columns = [
    {
      title: 'id',
      dataIndex: 'id',
    },
    {
      title: '用户名',
      dataIndex: 'username',
      render: text => <a>{text}</a>,
    },
    {
      title: '电话号码',
      dataIndex: 'phoneNumber',
    },
    {
      title: '操作',
      dataIndex: 'option',
      render: (_, record) => (
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
    router.push({
      pathname: '/system/users',
      query: { page },
    });
  }

  return (
    <div>
      <div id="components-table-demo-basic">
        <ProTable
          options={{ density: true, fullScreen: true, setting: true }}
          rowKey="id"
          pagination={false}
          total={systemUser.total}
          toolBarRender={(action, { selectedRows }) => [
            <Button type="primary" onClick={() => updateUserHandler({})}>
              新建用户
            </Button>,
          ]}
          headerTitle="用户列表"
          search={false}
          columns={columns}
          loading={isLoading}
          dataSource={systemUser.list}
        />
        <UserModal />
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
export default connect(({ systemUser, loading }) => ({
  systemUser,
  loading,
}))(UserTable);
