import React from 'react';
import { Divider, Popconfirm, Tag, Button } from 'antd';
import ProTable from '@ant-design/pro-table';
import SysMetadataModal from '../SysMetadataModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const SysMetadataTable = ({ dispatch, systemSysMetadata, loading }) => {
  const isLoading = loading.effects['systemSysMetadata/list'];

  const columns = [
    {
      title: 'id',
      dataIndex: 'id',
    },
    {
      title: systemSysMetadata?.sysMetadataGroup?.name ?? '选项值',
      dataIndex: 'label',
    },
    {
      title: '编号',
      dataIndex: 'groupNo',
      render: (groupNo) => {
        return <>{systemSysMetadata?.sysMetadataGroup?.tag ?? ''}{groupNo.padStart(systemSysMetadata?.sysMetadataGroup?.minKeyLength ?? 0, "0")}</>
      },
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
          <a onClick={() => updateSysMetadataHandler(record)}>修改</a>
          <Divider type="vertical" />
          <Popconfirm title="确认删除?" onConfirm={() => removeSysMetadataHandler(record.id)}>
            <a>删除</a>
          </Popconfirm>
        </span>
      ),
    },
  ];

  function removeSysMetadataHandler(id) {
    dispatch({
      type: 'systemSysMetadata/removeSysMetadata',
      payload: { id },
    });
  }

  function updateSysMetadataHandler(record) {
    dispatch({
      type: 'systemSysMetadata/setSysMetadata',
      payload: { sysMetadata: record },
    });
  }

  function pageChangedHandler(page) {
    router.push({
      pathname: '/system/sysMetadatas',
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
          total={systemSysMetadata.total}
          toolBarRender={(action, { selectedRows }) => [<SysMetadataModal />]}
          headerTitle={!isLoading && (systemSysMetadata?.sysMetadataGroup?.name??"元数据")+"列表"}
          search={false}
          columns={columns}
          loading={isLoading}
          dataSource={systemSysMetadata.list}
        />
        <Pagination
          className="ant-table-pagination"
          onChange={pageChangedHandler}
          defaultCurrent={6}
          total={systemSysMetadata.total}
          current={systemSysMetadata.current}
        />
      </div>
    </div>
  );
};
export default connect(({ systemSysMetadata, loading }) => ({
  systemSysMetadata,
  loading,
}))(SysMetadataTable);
