import React, { useEffect } from 'react';
import { Modal, Button, Form, Input, InputNumber, TreeSelect, Select } from 'antd';

import { connect } from 'dva';
import SysUser from '@/pages/system/SysUser';

const { Option } = Select;

const { SHOW_PARENT } = TreeSelect;

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

const SysRoleModal = ({ dispatch, systemSysRole, systemSysPermission, loading }) => {
    const [form] = Form.useForm();

    const updateLoading = loading.effects['systemSysRole/updateSysRole'];
    const saveLoading = loading.effects['systemSysRole/saveSysRole'];

    function showSysRoleModal() {
        dispatch({
            type: 'systemSysRole/setSysRole',
            payload: { sysRole: {} },
        });
    }

    useEffect(() => {
        dispatch({
            type: 'systemSysPermission/getPermissionTree',
            payload: { keyword: '' },
        });
        dispatch({
            type: 'systemSysRole/getRoleTree',
            payload: { keyword: '' },
        });
    }, [])

    useEffect(() => {
        if (systemSysRole.sysRole !== null) {
            form.resetFields();
            let permissionIds = [];
            for (let permission of systemSysRole.sysRole?.permissions ?? []) {
                permissionIds.push(permission.key);
            }
            let sysRole = { ...systemSysRole.sysRole, permissionIds }
            sysRole.parentId = sysRole.parentId ? sysRole.parentId.toString() : null;
            form.setFieldsValue({ sysRole });
        }
    }, [systemSysRole]);

    function handleOk() {
        form
            .validateFields()
            .then(values => {
                // 拆权限组选选中项
                let spanedPermissionId = [];
                for (let permissionIds of values?.sysRole?.permissionIds) {
                    for (let permissionId of permissionIds.split(',').map(x => +x)) {
                        spanedPermissionId = spanedPermissionId.concat(permissionId);
                    }
                }
                values.sysRole.permissionIds = spanedPermissionId;
                if (systemSysRole.sysRole?.id) {
                    values.sysRole.id = systemSysRole.sysRole.id;
                    dispatch({
                        type: 'systemSysRole/updateSysRole',
                        payload: values,
                    });
                } else {
                    dispatch({
                        type: 'systemSysRole/saveSysRole',
                        payload: values,
                    });
                }
            })
            .catch(err => console.log(err));
    }

    function handleCancel() {
        if (updateLoading || saveLoading) {
            return;
        }
        dispatch({
            type: 'systemSysRole/setSysRole',
            payload: { sysRole: null },
        });
    }

    return (
        <div>
            <Button type="primary" onClick={() => showSysRoleModal()}>新增角色</Button>
            <Modal
                title={(systemSysRole?.sysRole?.id ? '修改' : '新增') + '角色'}
                visible={systemSysRole.sysRole !== null}
                onOk={handleOk}
                maskClosable={false}
                forceRender={true}
                confirmLoading={updateLoading || saveLoading}
                destroyOnClose={false}
                cancelButtonProps={{ disabled: updateLoading || saveLoading }}
                onCancel={handleCancel}
            >
                <Form {...layout} form={form} name="system-sysRole-from">
                    <Form.Item name={['sysRole', 'role']}
                        label="角色名称"
                        rules={[
                            { max: 100, message: '角色名称不能大于100个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysRole', 'parentId']}
                        label="父角色"
                    >
                        <Select
                            getPopupContainer={triggerNode => triggerNode.parentNode}
                            showSearch={true}
                            optionFilterProp="title"
                            optionLabelProp="title"
                            allowClear={true}
                        >
                            {systemSysRole.roleTree.map(role => {
                                if (+role.key === systemSysRole.sysRole?.id) {
                                    return;
                                }
                                return <Option key={role.key} title={role.title}>{role.title}</Option>
                            })}
                        </Select>
                    </Form.Item>
                    <Form.Item name={['sysRole', 'description']}
                        label="角色描述"
                        rules={[
                            { max: 100, message: '角色描述不能大于100个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysRole', 'permissionIds']}
                        label="权限"
                    >
                        <TreeSelect placeholder='请为角色选择权限'
                            showCheckedStrategy={SHOW_PARENT}
                            getPopupContainer={triggerNode => triggerNode.parentNode}
                            treeCheckable={true}
                            treeNodeFilterProp="title"
                            treeData={systemSysPermission.permissionTree} />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default connect(({ systemSysRole, systemSysPermission, loading }) => ({
    systemSysRole,
    systemSysPermission,
    loading,
}))(SysRoleModal);
