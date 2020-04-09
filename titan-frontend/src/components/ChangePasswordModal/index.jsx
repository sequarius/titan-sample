import React, { useEffect } from 'react';
import { Modal, Form, Input, InputNumber } from 'antd';

import { connect } from 'dva';

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

const ChangePasswordModal = ({ dispatch, common, loading }) => {
    const [form] = Form.useForm();

    const updateLoading = loading.effects['common/changePassword'];

    useEffect(() => {
        if (common?.passwordModalShow) {
            form.resetFields();
        }
    }, [common.passwordModalShow]);

    function handleOk() {
        form
            .validateFields()
            .then(values => {
                dispatch({
                    type: 'common/changePassword',
                    payload: values,
                });
                console.log(values);
            })
            .catch(err => console.log(err));
    }

    function handleCancel() {
        dispatch({
            type: 'common/setPasswordModalShow',
            payload: false,
        });
    }

    return (
        <div>
            <Modal
                title="修改密码"
                visible={common.passwordModalShow}
                onOk={handleOk}
                maskClosable={false}
                forceRender={true}
                confirmLoading={updateLoading}
                cancelButtonProps={{ disabled: updateLoading  }}
                onCancel={handleCancel}
            >
                <Form {...layout} form={form} name="system-sysMetadataGroup-from">
                    <Form.Item name={['password', 'originPassword']}
                        label="原密码"
                        rules={[
                            { max: 120, message: '原密码不能超过128个字符' },
                            { required: true, message: "原密码不能为空!" }
                        ]}
                    >
                        <Input.Password />
                    </Form.Item>
                    <Form.Item name={['password', 'password']}
                        label="新密码"
                        rules={[
                            { max: 50, message: '新密码不能超过128个字符!' },
                            { min: 6, message: "新密码不能少于6个字符" },
                            { required: true, message: "新密码不能为空！" }
                        ]}
                    >
                        <Input.Password />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default connect(({ common, loading }) => ({
    common,
    loading,
}))(ChangePasswordModal);
