import React, { useEffect } from 'react';
import { Modal, Button, Form, Input, InputNumber, Switch, DatePicker} from 'antd';

import { connect } from 'dva';
import moment from 'moment';

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

const { TextArea } = Input;

const SysConfigModal = ({ dispatch, systemSysConfig, loading }) => {
    const [form] = Form.useForm();

    const updateLoading = loading.effects['systemSysConfig/updateSysConfig'];
    const saveLoading = loading.effects['systemSysConfig/saveSysConfig'];

    function showSysConfigModal() {
        dispatch({
            type: 'systemSysConfig/setSysConfig',
            payload: { sysConfig: {} },
        });
    }

    useEffect(() => {
        if (systemSysConfig.sysConfig !== null) {
            form.resetFields();
            let sysConfig  = { ...systemSysConfig.sysConfig, }
            form.setFieldsValue({ sysConfig: sysConfig  });
        }
    },[systemSysConfig]);

    function handleOk() {
        form
            .validateFields()
            .then(values => {
                if (systemSysConfig.sysConfig?.id) {
                    values.sysConfig.id = systemSysConfig.sysConfig.id;
                    dispatch({
                        type: 'systemSysConfig/updateSysConfig',
                        payload: values,
                    });
                } else {
                    dispatch({
                        type: 'systemSysConfig/saveSysConfig',
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
            type: 'systemSysConfig/setSysConfig',
            payload: { sysConfig: null },
        });
    }

    return (
        <div>
            <Button type="primary" onClick={()=>showSysConfigModal()}>新增系统配置</Button>
            <Modal
                title={(systemSysConfig?.sysConfig?.id ? '修改' : '新增') + '系统配置'}
                visible={systemSysConfig.sysConfig !== null}
                onOk={handleOk}
                maskClosable={false}
                forceRender={true}
                confirmLoading={updateLoading || saveLoading}
                cancelButtonProps={{ disabled: updateLoading || saveLoading }}
                onCancel={handleCancel}
            >
                <Form {...layout} form={form} name="system-sysConfig-from">
                    <Form.Item name={['sysConfig', 'key']}
                        label="配置项"
                        rules={[
                            { max: 128, message: '配置项不能大于128个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysConfig', 'publicConfig']}
                        label="公开配置"
                        valuePropName = 'checked'
                        rules={[
                        ]}
                    >
                        <Switch />
                    </Form.Item>
                    <Form.Item name={['sysConfig', 'describe']}
                        label="配置描述"
                        rules={[
                            { max: 256, message: '配置描述不能大于256个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name={['sysConfig', 'value']}
                        label="配置值"
                        rules={[
                            { max: 65535, message: '配置值不能大于65,535个字符!' },
                        ]}
                    >
                        <TextArea autoSize  />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default connect(({ systemSysConfig, loading }) => ({
    systemSysConfig,
    loading,
}))(SysConfigModal);
