import React, { useEffect } from 'react';
import { Modal, Button, Form, Input, InputNumber, Switch } from 'antd';

import { connect } from 'dva';

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};

const SysU2Modal = ({ dispatch, systemSysU2, loading }) => {
  const [form] = Form.useForm();

  const updateLoading = loading.effects['systemSysU2/updateSysU2'];
  const saveLoading = loading.effects['systemSysU2/saveSysU2'];

  useEffect(() => {
    if (systemSysU2.sysU2 !== null) {
      form.resetFields();
      form.setFieldsValue({ sysU2: systemSysU2.sysU2 });
    }
  }, [systemSysU2]);

  function handleOk() {
    form
      .validateFields()
      .then(values => {
        form.submit();
        if (systemSysU2.sysU2?.id) {
          values.sysU2.id = systemSysU2.sysU2.id;
          dispatch({
            type: 'systemSysU2/updateSysU2',
            payload: values,
          });
        } else {
          dispatch({
            type: 'systemSysU2/saveSysU2',
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
      type: 'systemSysU2/setSysU2',
      payload: { sysU2: null },
    });
  }

  return (
    <div>
      <Modal
        title={(systemSysU2?.sysU2?.id ? '修改' : '新增') + '用户'}
        visible={systemSysU2.sysU2 !== null}
        onOk={handleOk}
        maskClosable={false}
        forceRender={true}
        confirmLoading={updateLoading || saveLoading}
        cancelButtonProps={{ disabled: updateLoading || saveLoading }}
        onCancel={handleCancel}
      >
        <Form {...layout} form={form} name="system-sysU2-from">
          <Form.Item
            name={['sysU2', 'username']}
            label="登录名"
            rules={[
              { required: true, message: '登录名为必填项！' },
              { max: 16, message: '登录名不能大于16个字符!' },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name={['sysU2', 'password']}
            label="密码"
            rules={[{ max: 92, message: '密码不能大于92个字符!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name={['sysU2', 'passwordSalt']}
            label="salt"
            rules={[{ max: 32, message: 'salt不能大于32个字符!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name={['sysU2', 'phoneNumber']}
            label="电话号码"
            rules={[
              { required: true, message: '电话号码为必填项！' },
              { max: 11, message: '电话号码不能大于11个字符!' },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name={['sysU2', 'guid']}
            label="预留防坑字段"
            rules={[
              { max: 10000000000, type: 'number', message: '预留防坑字段不能大于10000000000' },
            ]}
          >
            <InputNumber />
          </Form.Item>
          <Form.Item
            name={['sysU2', 'locked']}
            label="是否被冻结"
            valuePropName="checked"
            rules={[]}
          >
            <Switch />
          </Form.Item>
          <Form.Item
            name={['sysU2', 'lastSignInIp']}
            label="ip"
            rules={[{ max: 64, message: 'ip不能大于64个字符!' }]}
          >
            <Input />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default connect(({ systemSysU2, loading }) => ({
  systemSysU2,
  loading,
}))(SysU2Modal);
