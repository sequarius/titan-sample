import React from 'react';
import { Divider, Popconfirm, Tag, Button } from 'antd';
import ProTable from '@ant-design/pro-table';
import SysU2Modal from '../SysU2Modal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const SysU2Table = ({ dispatch, systemSysU2, loading }) => {
  const isLoading = loading.effects['systemSysU2/list'];

  const columns = [
    {
      title: 'id',
      dataIndex: 'id',
    },
    {
      title: '登录名',
      dataIndex: 'username',
    },
    {
      title: 'salt',
      dataIndex: 'passwordSalt',
    },
    {
      title: '电话号码',
      dataIndex: 'phoneNumber',
    },
    {
      title: '预留防坑字段',
      dataIndex: 'guid',
    },
    {
      title: '是否被冻结',
      dataIndex: 'locked',
      render: (field, _) => (
        <>
          {field && <Tag color="success">√</Tag>}
          {!field && <Tag color="error">×</Tag>}
        </>
      ),
    },
    {
      title: 'ip',
      dataIndex: 'lastSignInIp',
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
          <Divider type="vertical" />
        </span>
      ),
    },
  ];

  function removeSysU2Handler(id) {
    dispatch({
      type: 'systemSysU2/removeSysU2',
      payload: { id },
    });
  }

  function updateSysU2Handler(record) {
    dispatch({
      type: 'systemSysU2/setSysU2',
      payload: { sysU2: record },
    });
  }

  function pageChangedHandler(page) {
    router.push({
      pathname: '/system/sysU2s',
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
          total={systemSysU2.total}
          toolBarRender={(action, { selectedRows }) => [
            <Button type="primary" onClick={() => updateSysU2Handler({})}>
              新建用户22
            </Button>,
          ]}
          headerTitle="用户22列表"
          search={false}
          columns={columns}
          loading={isLoading}
          dataSource={systemSysU2.list}
        />
        <SysU2Modal />
        <Pagination
          className="ant-table-pagination"
          onChange={pageChangedHandler}
          defaultCurrent={6}
          total={systemSysU2.total}
          current={systemSysU2.current}
        />
      </div>
    </div>
  );
};
export default connect(({ systemSysU2, loading }) => ({
  systemSysU2,
  loading,
}))(SysU2Table);
