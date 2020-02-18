import React, { useRef, useEffect } from 'react';
import { Divider, Popconfirm } from 'antd';
import ProTable from '@ant-design/pro-table';
import UserModal from '../UserModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const UserTable = ({ dispatch, systemUser, loading }) => {
  const isLoading = loading.effects['systemUser/list'];

  const ref = useRef();

  useEffect(() => {
    console.log('useRef');
    console.log(ref.current);
    ref.current.reload = dispatch({
      type: 'systemUser/list',
      payload: { page: 1 },
    });
  }, []);

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
    router.push({
      pathname: '/system/users',
      query: { page },
    });
  }

  return (
    <div>
      <div id="components-table-demo-basic">
        <ProTable
          rowKey="id"
          actionRef={ref}
          pagination={false}
          total={systemUser.total}
          toolBarRender={(action, { selectedRows }) => [<UserModal />]}
          headerTitle="用户列表"
          search={false}
          columns={columns}
          loading={isLoading}
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
export default connect(({ systemUser, loading }) => ({
  systemUser,
  loading,
}))(UserTable);
