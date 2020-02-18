import React, { useEffect } from 'react';
import { Modal, Button, Form, Input, InputNumber, Switch } from 'antd';

import { connect } from 'dva';

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};

// import { connect } from 'puppeteer';

const UserModal = ({ dispatch, systemUser }) => {
  const [form] = Form.useForm();

  function showModal() {
    dispatch({
      type: 'systemUser/setUser',
      payload: { user: {} },
    });
  }

  useEffect(() => {
    form.setFieldsValue({ user: systemUser.user });
  });

  function handleOk() {
    form
      .validateFields()
      .then(values => {
        form.submit();
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

  function handleCancel(e) {
    dispatch({
      type: 'systemUser/setUser',
      payload: { user: null },
    });
    Modal.destroyAll();
  }

  return (
    <div>
      <Button type="primary" onClick={showModal}>
        新建用户
      </Button>
      <Modal
        title={(systemUser?.user?.id ? '修改' : '新增') + '用户'}
        visible={systemUser.user !== null}
        onOk={handleOk}
        maskClosable={false}
        forceRender={true}
        onCancel={handleCancel}
      >
        <Form {...layout} form={form} name="system-user-from">
          <Form.Item name={['user', 'username']} label="用户名" rules={[{ required: true }]}>
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
    </div>
  );
};

export default connect(({ systemUser }) => ({
  systemUser,
}))(UserModal);
