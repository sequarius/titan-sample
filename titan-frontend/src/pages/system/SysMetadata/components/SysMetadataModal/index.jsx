import React, { useState, useEffect } from 'react';
import { Modal, Button, Form, Input, Tooltip, Switch, DatePicker } from 'antd';
import { QuestionCircleOutlined } from '@ant-design/icons';
import { connect } from 'dva';
import moment from 'moment';

const layout = {
    labelCol: { span: 10 },
    wrapperCol: { span: 14 },
};

const SysMetadataModal = ({ dispatch, systemSysMetadata, loading }) => {
    const [form] = Form.useForm();

    const updateLoading = loading.effects['systemSysMetadata/updateSysMetadata'];
    const saveLoading = loading.effects['systemSysMetadata/saveSysMetadata'];
    const isLoading = loading.effects['systemSysMetadata/list'];

    function showSysMetadataModal() {
        dispatch({
            type: 'systemSysMetadata/setSysMetadata',
            payload: { sysMetadata: {} },
        });
    }

    const [autoNo, setAutoNo] = useState(true);

    useEffect(() => {
        if (systemSysMetadata.sysMetadata !== null) {
            form.resetFields();
            let sysMetadata = { ...systemSysMetadata.sysMetadata }
            sysMetadata.groupNo = sysMetadata?.groupNo?.padStart(systemSysMetadata?.sysMetadataGroup?.minKeyLength ?? 0, "0")
            form.setFieldsValue({ sysMetadata: sysMetadata });
        }
        if (systemSysMetadata?.sysMetadata?.id) {
            setAutoNo(false);
        }else{
            setAutoNo(true);
        }
    }, [systemSysMetadata]);

    function handleOk() {
        form
            .validateFields()
            .then(values => {
                values.sysMetadata.groupId = systemSysMetadata?.sysMetadataGroup?.id;
                if (autoNo) {
                    values.sysMetadata.groupNo = null;
                } else {
                    values.sysMetadata.groupNo = values.sysMetadata?.groupNo?.replace(/^0+/, '');
                }
                if (systemSysMetadata.sysMetadata?.id) {
                    values.sysMetadata.id = systemSysMetadata.sysMetadata.id;
                    dispatch({
                        type: 'systemSysMetadata/updateSysMetadata',
                        payload: values,
                    });
                } else {
                    dispatch({
                        type: 'systemSysMetadata/saveSysMetadata',
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
            type: 'systemSysMetadata/setSysMetadata',
            payload: { sysMetadata: null },
        });
    }

    return (
        <div>
            {!isLoading && <Button type="primary" onClick={() => showSysMetadataModal()}>{'新增'+ systemSysMetadata?.sysMetadataGroup?.name??'元数据'}</Button>}
            <Modal
                title={(systemSysMetadata?.sysMetadata?.id ? '修改' : '新增') + systemSysMetadata?.sysMetadataGroup?.name??'元数据'}
                visible={systemSysMetadata.sysMetadata !== null}
                onOk={handleOk}
                maskClosable={false}
                forceRender={true}
                confirmLoading={updateLoading || saveLoading}
                cancelButtonProps={{ disabled: updateLoading || saveLoading }}
                onCancel={handleCancel}
            >
                <Form {...layout} form={form} name="system-sysMetadata-from">
                    <Form.Item name={['sysMetadata', 'label']}
                        label="显示名"
                        rules={[
                            { max: 512, message: '显示名不能大于512个字符!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    {(systemSysMetadata?.sysMetadata && !systemSysMetadata?.sysMetadata?.id) && <Form.Item
                        label={
                            <span>
                                自动编码&nbsp;
                               <Tooltip title="按当前最大编码+1作为编码"
                                    getPopupContainer={triggerNode => triggerNode.parentNode}
                                    placement="top"
                                >
                                    <QuestionCircleOutlined />
                                </Tooltip>
                            </span>

                        }
                    >
                        <Switch checked={autoNo} onChange={(value) => { setAutoNo(value) }} />
                    </Form.Item>
                    }
                    {(systemSysMetadata?.sysMetadata?.id || !autoNo) && <Form.Item name={['sysMetadata', 'groupNo']}
                        label="数据编码"
                        rules={[
                            { max: 32, message: '数据编码不能大于32个字符!' },
                        ]}
                    >
                        <Input addonBefore={systemSysMetadata?.sysMetadataGroup?.tag} />
                    </Form.Item>
                    }
                </Form>
            </Modal>
        </div>
    );
};

export default connect(({ systemSysMetadata, loading }) => ({
    systemSysMetadata,
    loading,
}))(SysMetadataModal);
