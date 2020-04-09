import React, { useEffect } from 'react';
import { Modal, Button, Form, Input, InputNumber, Switch, DatePicker } from 'antd';

import { connect } from 'dva';
import moment from 'moment';

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

const SysMetadataGroupModal = ({ dispatch, systemSysMetadataGroup, loading }) => {
    const [form] = Form.useForm();

    const updateLoading = loading.effects['systemSysMetadataGroup/updateSysMetadataGroup'];
    const saveLoading = loading.effects['systemSysMetadataGroup/saveSysMetadataGroup'];

    function showSysMetadataGroupModal() {
        dispatch({
            type: 'systemSysMetadataGroup/setSysMetadataGroup',
            payload: { sysMetadataGroup: {} },
        });
    }

    useEffect(() => {
        if (systemSysMetadataGroup.sysMetadataGroup !== null) {
            form.resetFields();
            let sysMetadataGroup = { ...systemSysMetadataGroup.sysMetadataGroup }
            if (!sysMetadataGroup.minKeyLength) {
                sysMetadataGroup.minKeyLength = 0;
            }
            form.setFieldsValue({ sysMetadataGroup: sysMetadataGroup });
        }
    }, [systemSysMetadataGroup]);

    function handleOk() {
        form
            .validateFields()
            .then(values => {
                if (systemSysMetadataGroup.sysMetadataGroup?.id) {
                    values.sysMetadataGroup.id = systemSysMetadataGroup.sysMetadataGroup.id;
                    dispatch({
                        type: 'systemSysMetadataGroup/updateSysMetadataGroup',
                        payload: values,
                    });
                } else {
                    dispatch({
                        type: 'systemSysMetadataGroup/saveSysMetadataGroup',
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
            type: 'systemSysMetadataGroup/setSysMetadataGroup',
            payload: { sysMetadataGroup: null },
        });
    }

    return (
        <div>
            <Button type="primary" onClick={() => showSysMetadataGroupModal()}>新增元数据集</Button>
            <Modal
                title={(systemSysMetadataGroup?.sysMetadataGroup?.id ? '修改' : '新增') + '元数据集'}
                visible={systemSysMetadataGroup.sysMetadataGroup !== null}
                onOk={handleOk}
                maskClosable={false}
                forceRender={true}
                confirmLoading={updateLoading || saveLoading}
                cancelButtonProps={{ disabled: updateLoading || saveLoading }}
                onCancel={handleCancel}
            >
                <Form {...layout} form={form} name="system-sysMetadataGroup-from">
                    <Form.Item name={['sysMetadataGroup', 'key']}
                        label="元数据集键"
                        rules={[
                            { max: 120, message: '元数据集键不能大于120个字符!' },
                            { required: true, message: "元数据集键未填写！" }
                        ]}
                    >
                        <Input disabled={systemSysMetadataGroup?.sysMetadataGroup?.id} />
                    </Form.Item>
                    <Form.Item name={['sysMetadataGroup', 'name']}
                        label="元数据集名称"
                        rules={[
                            { max: 50, message: '元数据集名称不能大于50个字符!' },
                            { required: true, message: "元数据集名称未填写！" }
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysMetadataGroup', 'tag']}
                        label="数据前缀	"
                        rules={[
                            { max: 50, message: '数据前缀不能大于50个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysMetadataGroup', 'minKeyLength']}
                        label="最小编码长度"
                        rules={[
                            { max: 9223372036854775807, type: "number", message: '最小编码长度不能大于9223372036854775807' },
                        ]}
                    >
                        <InputNumber />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default connect(({ systemSysMetadataGroup, loading }) => ({
    systemSysMetadataGroup,
    loading,
}))(SysMetadataGroupModal);
