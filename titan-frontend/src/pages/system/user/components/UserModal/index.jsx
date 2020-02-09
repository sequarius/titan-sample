import React, { useState } from 'react';
import { Modal, Button, Form, Input, InputNumber } from 'antd';
import styles from './index.less';

import { connect } from 'dva';

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};

// import { connect } from 'puppeteer';

function UserModal() {

  const [visible, setVisible] = useState(false);

  const [form] = Form.useForm();

  function showModal() {
    setVisible(true)
  };

  function handleOk() {
    form.validateFields().then(() => {
      form.submit();
      setVisible(false)
    }).catch(err=>console.log(err));
  };

  function handleCancel(e) {
    console.log(e);
    setVisible(false);
  };

  function onFinish(values) {
    console.log(values);
  }


  return (
    <div>
      <Button type="primary" onClick={showModal}>
        新建用户
        </Button>
      <Modal
        title="Basic Modal"
        visible={visible}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Form {...layout} form={form} name="nest-messages" onFinish={onFinish}>
          <Form.Item name={['user', 'name']} label="用户名" rules={[{ required: true }]}>
            <Input />
          </Form.Item>
          <Form.Item name={['user', 'email']} label="密码">
            <Input />
          </Form.Item>
          <Form.Item name={['user', 'age']} label="年龄">
            <InputNumber />
          </Form.Item>
          <Form.Item name={['user', 'website']} label="部门">
            <Input />
          </Form.Item>
          <Form.Item name={['user', 'introduction']} label="Introduction">
            <Input.TextArea />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );

}


// export default FormWapper
export default () => (
  <div className={styles.container}>
    <div id="components-modal-demo-basic">
      <UserModal />
    </div>
  </div>
);
