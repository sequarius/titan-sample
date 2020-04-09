import React, { useEffect } from 'react';
import { Modal, Button, Form, Input, InputNumber, Switch, DatePicker} from 'antd';

import { connect } from 'dva';
import moment from 'moment';

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

const SysPermissionModal = ({ dispatch, systemSysPermission, loading }) => {
    const [form] = Form.useForm();

    const updateLoading = loading.effects['systemSysPermission/updateSysPermission'];
    const saveLoading = loading.effects['systemSysPermission/saveSysPermission'];

    function showSysPermissionModal() {
        dispatch({
            type: 'systemSysPermission/setSysPermission',
            payload: { sysPermission: {} },
        });
    }

    useEffect(() => {
        if (systemSysPermission.sysPermission !== null) {
            form.resetFields();
            let sysPermission  = { ...systemSysPermission.sysPermission, }
            form.setFieldsValue({ sysPermission: sysPermission  });
        }
    },[systemSysPermission]);

    function handleOk() {
        form
            .validateFields()
            .then(values => {
                if (systemSysPermission.sysPermission?.id) {
                    values.sysPermission.id = systemSysPermission.sysPermission.id;
                    dispatch({
                        type: 'systemSysPermission/updateSysPermission',
                        payload: values,
                    });
                } else {
                    dispatch({
                        type: 'systemSysPermission/saveSysPermission',
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
            type: 'systemSysPermission/setSysPermission',
            payload: { sysPermission: null },
        });
    }

    return (
        <div>
            {/* <Button type="primary" onClick={()=>showSysPermissionModal()}>新增权限</Button> */}
            <Modal
                title={(systemSysPermission?.sysPermission?.id ? '修改' : '新增') + '权限'}
                visible={systemSysPermission.sysPermission !== null}
                onOk={handleOk}
                maskClosable={false}
                forceRender={true}
                confirmLoading={updateLoading || saveLoading}
                cancelButtonProps={{ disabled: updateLoading || saveLoading }}
                onCancel={handleCancel}
            >
                <Form {...layout} form={form} name="system-sysPermission-from">
                    <Form.Item name={['sysPermission', 'permission']}
                        label="权限"
                        rules={[
                            { max: 100, message: '权限不能大于100个字符!' },
                        ]}
                    >
                        <Input disabled/>
                    </Form.Item>
                    <Form.Item name={['sysPermission', 'description']}
                        label="描述"
                        rules={[
                            { max: 100, message: '描述不能大于100个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysPermission', 'group']}
                        label="分组"
                        rules={[
                            { max: 100, message: '分组不能大于100个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default connect(({ systemSysPermission, loading }) => ({
    systemSysPermission,
    loading,
}))(SysPermissionModal);
