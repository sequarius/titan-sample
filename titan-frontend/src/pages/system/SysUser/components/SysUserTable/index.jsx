import React from 'react';
import { Divider, Popconfirm, Tag, Input  } from 'antd';
import ProTable from '@ant-design/pro-table';
import SysUserModal from '../SysUserModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const { Search } = Input;

const SysUserTable = ({ dispatch, systemSysUser, loading }) => {
  const isLoading = loading.effects['systemSysUser/list'];

  const columns = [
    {
        title: 'id',
        dataIndex: 'id',
    },
    {
        title: '账号',
        dataIndex: 'username',
    },
    {
      title: '姓名',
      dataIndex: 'name',
    },
    {
      title: '角色',
      dataIndex: 'role',
      render: (_, record) => (
        record.roles?.map((role,index)=> 
          <Tag color="#2db7f5" key={index}>{role.value}</Tag>
        )
      ),
    },
    {
        title: '是否被冻结',
        dataIndex: 'locked',
        render: (field, _) => (
          <>
            {field && <Tag color="success">√</Tag>}
            {!field && <Tag color="error">×</Tag>}
          </>
        )
    },
    {
        title: '最后登陆IP',
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
      fixed: 'right',
      render: (_, record) => (
        <span>
          <a onClick={() => updateSysUserHandler(record)}>修改</a>
          <Divider type="vertical" />
          <Popconfirm title="确认删除?" onConfirm={() => removeSysUserHandler(record.id)}>
            <a>删除</a>
          </Popconfirm>
        </span>
      ),
    },
  ];

  function removeSysUserHandler(id) {
    dispatch({
      type: 'systemSysUser/removeSysUser',
      payload: { id },
    });
  }

  function updateSysUserHandler(record) {
    dispatch({
      type: 'systemSysUser/setSysUser',
      payload: { sysUser: record },
    });
  }

  function pageChangedHandler(page) {
    router.push({
      pathname: '/system/sysUsers',
      query: { page ,keyword:systemSysUser.keyword},
    });
  }

  function onSearch(keyword) {
      router.push({
        pathname: '/system/sysUsers',
        query: { page: 1, keyword  },
      });
  }

  return (
    <div>
      <div id="components-table-demo-basic">
        <ProTable
          options={{ density: true, fullScreen: true, setting: true }}
          rowKey="id"
          pagination={false}
          total={systemSysUser.total}
          toolBarRender={(action, { selectedRows }) => [
            <Search allowClear style={{marginTop:'16px'}} placeholder="搜索用户" enterButton onSearch={(value)=>onSearch(value)}/>,
            <SysUserModal />]
          }
          headerTitle="用户列表"
          search={false}
          scroll={{ x: 1400 }}
          columns={columns}
          loading={isLoading}
          dataSource={systemSysUser.list}
        />
        <Pagination
          className="ant-table-pagination"
          onChange={pageChangedHandler}
          defaultCurrent={6}
          total={systemSysUser.total}
          current={systemSysUser.current}
        />
      </div>
    </div>
  );
};
export default connect(({ systemSysUser, loading }) => ({
  systemSysUser,
  loading,
}))(SysUserTable);
