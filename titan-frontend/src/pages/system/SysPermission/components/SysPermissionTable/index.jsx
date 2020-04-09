import React from 'react';
import { Divider, Popconfirm, Tag, Button } from 'antd';
import ProTable from '@ant-design/pro-table';
import SysPermissionModal from '../SysPermissionModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const SysPermissionTable = ({ dispatch, systemSysPermission, loading }) => {
  const isLoading = loading.effects['systemSysPermission/list'];

  const columns = [
    {
        title: 'id',
        dataIndex: 'id',
    },
    {
        title: '权限',
        dataIndex: 'permission',
    },
    {
        title: '描述',
        dataIndex: 'description',
    },
    {
        title: '分组',
        dataIndex: 'group',
    },
    {
        title: '创建日期',
        dataIndex: 'createTime',
    },
    {
        title: '更新日期',
        dataIndex: 'updateTime',
    },
    {
      title: '操作',
      dataIndex: 'option',
      render: (_, record) => (
        <span>
          <a onClick={() => updateSysPermissionHandler(record)}>修改</a>
        </span>
      ),
    },
  ];

  function removeSysPermissionHandler(id) {
    dispatch({
      type: 'systemSysPermission/removeSysPermission',
      payload: { id },
    });
  }

  function updateSysPermissionHandler(record) {
    dispatch({
      type: 'systemSysPermission/setSysPermission',
      payload: { sysPermission: record },
    });
  }

  function pageChangedHandler(page) {
    router.push({
      pathname: '/system/sysPermissions',
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
          total={systemSysPermission.total}
          toolBarRender={(action, { selectedRows }) => [<SysPermissionModal />]}
          headerTitle="权限列表"
          search={false}
          columns={columns}
          loading={isLoading}
          dataSource={systemSysPermission.list}
        />
        <Pagination
          className="ant-table-pagination"
          onChange={pageChangedHandler}
          defaultCurrent={6}
          total={systemSysPermission.total}
          current={systemSysPermission.current}
        />
      </div>
    </div>
  );
};
export default connect(({ systemSysPermission, loading }) => ({
  systemSysPermission,
  loading,
}))(SysPermissionTable);
