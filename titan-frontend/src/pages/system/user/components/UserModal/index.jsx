import React, { useEffect } from 'react';
import { Modal, Button, Form, Input, InputNumber, Switch } from 'antd';

import { connect } from 'dva';

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};

// import { connect } from 'puppeteer';

const UserModal = ({ dispatch, systemUser, loading }) => {
  const [form] = Form.useForm();

  const upadteLoading = loading.effects['systemUser/updateUser'];
  const saveLoading = loading.effects['systemUser/saveUser'];

  useEffect(() => {
    if (systemUser.user !== null) {
      form.resetFields();
      form.setFieldsValue({ user: systemUser.user });
    }
  }, [systemUser]);

  function handleOk() {
    form
      .validateFields()
      .then(values => {
        if (systemUser.user?.id) {
          values.user.id = systemUser.user.id;
          dispatch({
            type: 'systemUser/updateUser',
            payload: values,
          });
        } else {
          dispatch({
            type: 'systemUser/saveUser',
            payload: values,
          });
        }
      })
      .catch(err => console.log(err));
  }

  function handleCancel() {
    if (upadteLoading || saveLoading) {
      return;
    }
    dispatch({
      type: 'systemUser/setUser',
      payload: { user: null },
    });
  }

  return (
    <Modal
      title={(systemUser?.user?.id ? '修改' : '新增') + '用户'}
      visible={systemUser.user !== null}
      onOk={handleOk}
      maskClosable={false}
      destroyOnClose
      forceRender={true}
      confirmLoading={upadteLoading || saveLoading}
      cancelButtonProps={{ disabled: upadteLoading || saveLoading }}
      onCancel={handleCancel}
    >
      <Form
        {...layout}
        form={form}
        initialValues={{ user: systemUser.user }}
        name="system-user-from"
      >
        <Form.Item
          name={['user', 'username']}
          label="用户名"
          rules={[
            { required: false, message: '用户名为必填项目' },
            { max: 3, message: '用户名不能超过3位' },
            { min: 1, message: '用户名不能少于1位' },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item name={['user', 'password']} label="密码">
          <Input />
        </Form.Item>
        <Form.Item name={['user', 'phoneNumber']} label="手机号" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item name={['user', 'age']} label="年龄">
          <InputNumber />
        </Form.Item>
        <Form.Item name={['user', 'locked']} label="冻结" valuePropName="checked">
          <Switch />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default connect(({ systemUser, loading }) => ({
  systemUser,
  loading,
}))(UserModal);
