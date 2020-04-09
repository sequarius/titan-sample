import React from 'react';
import { Divider, Popconfirm, Tag, Tooltip } from 'antd';
import {QuestionCircleOutlined} from '@ant-design/icons'
import ProTable from '@ant-design/pro-table';
import SysConfigModal from '../SysConfigModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const SysConfigTable = ({ dispatch, systemSysConfig, loading }) => {
  const isLoading = loading.effects['systemSysConfig/list'];

  const columns = [
    {
      title: 'id',
      dataIndex: 'id',
    },
    {
      title: '配置项',
      dataIndex: 'key',
      copyable: true
    },
    {
      title: '配置值',
      ellipsis: true,
      width: 300,
      dataIndex: 'value',
    },
    {
      title: <>
        <Tooltip title="公开后配置值可以被前端通过接口获取，可用作下拉选项" >
            公开配置 <QuestionCircleOutlined /> 
        </Tooltip>
      </>,
      dataIndex: 'publicConfig',
      render: (field, _) => (
        <>
          {field && <Tag color="success">√</Tag>}
          {!field && <Tag color="error">×</Tag>}
        </>
      )
    },
    {
      title: '配置描述',
      dataIndex: 'describe',
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
          <a onClick={() => updateSysConfigHandler(record)}>修改</a>
          <Divider type="vertical" />
          <Popconfirm title="确认删除?" onConfirm={() => removeSysConfigHandler(record.id)}>
            <a>删除</a>
          </Popconfirm>
        </span>
      ),
    },
  ];

  function removeSysConfigHandler(id) {
    dispatch({
      type: 'systemSysConfig/removeSysConfig',
      payload: { id },
    });
  }

  function updateSysConfigHandler(record) {
    dispatch({
      type: 'systemSysConfig/setSysConfig',
      payload: { sysConfig: record },
    });
  }

  function pageChangedHandler(page) {
    router.push({
      pathname: '/system/sysConfigs',
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
          total={systemSysConfig.total}
          toolBarRender={(action, { selectedRows }) => [<SysConfigModal />]}
          headerTitle="系统配置列表"
          search={false}
          columns={columns}
          loading={isLoading}
          dataSource={systemSysConfig.list}
        />
        <Pagination
          className="ant-table-pagination"
          onChange={pageChangedHandler}
          defaultCurrent={6}
          total={systemSysConfig.total}
          current={systemSysConfig.current}
        />
      </div>
    </div>
  );
};
export default connect(({ systemSysConfig, loading }) => ({
  systemSysConfig,
  loading,
}))(SysConfigTable);
