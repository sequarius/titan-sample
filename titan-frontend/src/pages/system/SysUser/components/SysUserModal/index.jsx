import React, { useEffect } from 'react';
import { Modal, Button, Form, Input, InputNumber, Switch, TreeSelect} from 'antd';

import { connect } from 'dva';

const { SHOW_PARENT } = TreeSelect;


const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

const SysUserModal = ({ dispatch, systemSysUser, systemSysRole,loading }) => {
    const [form] = Form.useForm();

    const updateLoading = loading.effects['systemSysUser/updateSysUser'];
    const saveLoading = loading.effects['systemSysUser/saveSysUser'];

    function showSysUserModal() {
        dispatch({
            type: 'systemSysUser/setSysUser',
            payload: { sysUser: {} },
        });
    }

    useEffect(() => {
        if (systemSysUser.sysUser !== null) {
            form.resetFields();

            let roleIds = [];
            for (let role of systemSysUser.sysUser?.roles ?? []) {
                roleIds.push(role.key);
            }
            let sysUser = { ...systemSysUser.sysUser, roleIds }
            form.setFieldsValue({ sysUser });
        }
    },[systemSysUser]);

    useEffect(() => {
        dispatch({
            type: 'systemSysRole/getRoleTree',
            payload: { keyword: '' },
        });
    }, [])

    function handleOk() {
        form
            .validateFields()
            .then(values => {
                let spanedRoleIds = [];
                for (let roleIds of values?.sysUser?.roleIds) {
                    for (let roleId of roleIds.split(',').map(x => +x)) {
                        spanedRoleIds = spanedRoleIds.concat(roleId);
                    }
                }
                values.sysUser.roleIds = spanedRoleIds;
                if (systemSysUser.sysUser?.id) {
                    values.sysUser.id = systemSysUser.sysUser.id;
                    dispatch({
                        type: 'systemSysUser/updateSysUser',
                        payload: values,
                    });
                } else {
                    dispatch({
                        type: 'systemSysUser/saveSysUser',
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
            type: 'systemSysUser/setSysUser',
            payload: { sysUser: null },
        });
    }

    return (
        <div>
            {/* <Button type="primary" onClick={()=>showSysUserModal()}>新增用户</Button> */}
            <Modal
                title={(systemSysUser?.sysUser?.id ? '修改' : '新增') + '用户'}
                visible={systemSysUser.sysUser !== null}
                onOk={handleOk}
                maskClosable={false}
                forceRender={true}
                confirmLoading={updateLoading || saveLoading}
                cancelButtonProps={{ disabled: updateLoading || saveLoading }}
                onCancel={handleCancel}
            >
                <Form {...layout} form={form} name="system-sysUser-from">
                    <Form.Item name={['sysUser', 'username']}
                        label="登录名"
                        rules={[
                            { required: true, message: '登录名为必填项！' },
                            { max: 16, message: '登录名不能大于16个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysUser', 'name']}
                        label="姓名"
                        rules={[
                            { required: true, message: '姓名为必填项！' },
                            { max: 16, message: '姓名不能大于16个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysUser', 'password']}
                        label="密码"
                        rules={[
                            { max: 128, message: '密码不能大于128个字符!' },
                        ]}
                    >
                        <Input placeholder='若不修改密码留空该项即可'/>
                    </Form.Item>
                    <Form.Item name={['sysUser', 'roleIds']}
                        label="角色"
                    >
                        <TreeSelect placeholder='请为用户分配角色'
                            showCheckedStrategy={SHOW_PARENT}
                            getPopupContainer={triggerNode => triggerNode.parentNode}
                            treeCheckable={true}
                            treeNodeFilterProp="title"
                            treeData={systemSysRole.roleTree} />
                    </Form.Item>
                    <Form.Item name={['sysUser', 'locked']}
                        label="是否被冻结"
                        valuePropName = 'checked'
                        rules={[
                        ]}
                    >
                        <Switch />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default connect(({ systemSysUser,systemSysRole, loading }) => ({
    systemSysUser,
    systemSysRole,
    loading,
}))(SysUserModal);
