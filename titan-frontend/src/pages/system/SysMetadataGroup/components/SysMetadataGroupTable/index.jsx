import React from 'react';
import { Divider, Popconfirm, Tag, Button } from 'antd';
import ProTable from '@ant-design/pro-table';
import SysMetadataGroupModal from '../SysMetadataGroupModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';
import Link from 'umi/link';

const SysMetadataGroupTable = ({ dispatch, systemSysMetadataGroup, loading }) => {
  const isLoading = loading.effects['systemSysMetadataGroup/list'];

  const columns = [
    {
      title: 'id',
      dataIndex: 'id', name
    },
    {
      title: '元数据组名称',
      dataIndex: 'name',
      render: (name, record) => (
        <Link to={`/system/sysMetadataGroups/${record.id}`}>{name}</Link>
      )
    },
    {
      title: '元数据组键',
      dataIndex: 'key',
      copyable: true
    },
    {
      title: '数据前缀',
      dataIndex: 'tag',
    },
    {
      title: '最小编码长度',
      dataIndex: 'minKeyLength',
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
      fixed: 'right',
      render: (_, record) => (
        <span>
          <a onClick={() => updateSysMetadataGroupHandler(record)}>修改</a>
          <Divider type="vertical" />
          <Popconfirm title="确认删除?" onConfirm={() => removeSysMetadataGroupHandler(record.id)}>
            <a>删除</a>
          </Popconfirm>
        </span>
      ),
    },
  ];

  function removeSysMetadataGroupHandler(id) {
    dispatch({
      type: 'systemSysMetadataGroup/removeSysMetadataGroup',
      payload: { id },
    });
  }

  function updateSysMetadataGroupHandler(record) {
    dispatch({
      type: 'systemSysMetadataGroup/setSysMetadataGroup',
      payload: { sysMetadataGroup: record },
    });
  }

  function pageChangedHandler(page) {
    router.push({
      pathname: '/system/sysMetadataGroups',
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
          total={systemSysMetadataGroup.total}
          toolBarRender={(action, { selectedRows }) => [<SysMetadataGroupModal />]}
          headerTitle="元数据组列表"
          search={false}
          columns={columns}
          scroll={{ x: 1300 }}
          loading={isLoading}
          dataSource={systemSysMetadataGroup.list}
        />
        <Pagination
          className="ant-table-pagination"
          onChange={pageChangedHandler}
          defaultCurrent={6}
          total={systemSysMetadataGroup.total}
          current={systemSysMetadataGroup.current}
        />
      </div>
    </div>
  );
};
export default connect(({ systemSysMetadataGroup, loading }) => ({
  systemSysMetadataGroup,
  loading,
}))(SysMetadataGroupTable);
