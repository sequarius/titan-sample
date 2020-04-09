import React from 'react';
import { Divider, Popconfirm, Tag, Button } from 'antd';
import ProTable from '@ant-design/pro-table';
import SysRoleModal from '../SysRoleModal';
import { connect } from 'dva';
import { Pagination } from 'antd';
import router from 'umi/router';

const SysRoleTable = ({ dispatch, systemSysRole, loading }) => {
  const isLoading = loading.effects['systemSysRole/list'];

  const columns = [
    {
      title: 'id',
      dataIndex: 'id',
    },
    {
      title: '角色名称',
      dataIndex: 'role',
    },
    {
      title: '角色描述',
      dataIndex: 'description',
    },
    {
      title: '父角色',
      dataIndex: 'parentId',
      render: (parentId) => (
        getRoleTitle(parentId)
      ),
    },
    {
      title: '权限',
      dataIndex: 'permission',
      width: 480,
      render: (_, record) => (
        record.permissions?.map((permission,index)=> 
          <Tag color="#2db7f5" key={index}>{permission.value}</Tag>
        )
      ),
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
          <a onClick={() => updateSysRoleHandler(record)}>修改</a>
          <Divider type="vertical" />
          <Popconfirm title="删除操作将同时取消拥有该角色的用户角色，确认删除角色?" onConfirm={() => removeSysRoleHandler(record.id)}>
            <a>删除</a>
          </Popconfirm>
        </span>
      ),
    },
  ];

  function removeSysRoleHandler(id) {
    dispatch({
      type: 'systemSysRole/removeSysRole',
      payload: { id },
    });
  }

  function updateSysRoleHandler(record) {
    dispatch({
      type: 'systemSysRole/setSysRole',
      payload: { sysRole: record },
    });
  }

  function pageChangedHandler(page) {
    router.push({
      pathname: '/system/sysRoles',
      query: { page },
    });
  }

  function getRoleTitle(key){
    for (let role of systemSysRole.roleTree){
        if(+role.key===key){
            return role.title;
        }
    }
    return key;
}

  return (
    <div>
      <div id="components-table-demo-basic">
        <ProTable
          options={{ density: true, fullScreen: true, setting: true }}
          rowKey="id"
          pagination={false}
          total={systemSysRole.total}
          toolBarRender={(action, { selectedRows }) => [<SysRoleModal />]}
          headerTitle="角色列表"
          search={false}
          columns={columns}
          scroll={{ x: 1800 }}
          loading={isLoading}
          dataSource={systemSysRole.list}
        />
        <Pagination
          className="ant-table-pagination"
          onChange={pageChangedHandler}
          defaultCurrent={6}
          total={systemSysRole.total}
          current={systemSysRole.current}
        />
      </div>
    </div>
  );
};
export default connect(({ systemSysRole, loading }) => ({
  systemSysRole,
  loading,
}))(SysRoleTable);
