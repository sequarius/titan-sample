import { AlipayCircleOutlined, TaobaoCircleOutlined, WeiboCircleOutlined } from '@ant-design/icons';
import { Alert, Checkbox, notification} from 'antd';
import React, { useState,useEffect} from 'react';
import { Link } from 'umi';
import { connect } from 'dva';
import styles from './style.less';
import LoginFrom from './components/Login';
const { UserName, Password, Submit } = LoginFrom;
import { getPageQuery } from '@/utils/utils';
import { router } from 'umi';

const Login = props => {
  const { submitting } = props;
  const [type, setType] = useState('account');

  const handleSubmit = values => {
    const { dispatch } = props;
    dispatch({
      type: 'common/login',
      payload: { ...values, type },
    });
  };

  useEffect(() => {
    let params = getPageQuery();
    const message = params.message;
    if(message){
      notification.warn({
        description: message,
        message: '提示',
        duration: 8,
      });
      delete params.message;
      router.replace({
        pathname: '/login',
        query: params
      })
    }
  }, []);

  return (
    <div className={styles.main}>
      <LoginFrom activeKey={type} onTabChange={setType} onSubmit={handleSubmit}>
          <UserName
            name="username"
            placeholder="用户名"
            rules={[
              {
                required: true,
                message: '请输入用户名!',
              },
            ]}
          />
          <Password
            name="password"
            placeholder="密码"
            rules={[
              {
                required: true,
                message: '请输入密码！',
              },
            ]}
          />
        <Submit loading={submitting}>登录</Submit>
        <div className={styles.other}>
          {/* 其他登录方式
          <AlipayCircleOutlined className={styles.icon} />
          <TaobaoCircleOutlined className={styles.icon} />
          <WeiboCircleOutlined className={styles.icon} /> */}
          <Link className={styles.register} to="/user/register">
            注册账户
          </Link>
          <a
            style={{
              float: 'right',
              marginRight: '20px'
            }}
          >
            忘记密码
          </a>
        </div>
      </LoginFrom>
    </div>
  );
};

export default connect(({ common, loading }) => ({
  userLogin: common,
  submitting: loading.effects['common/login'],
}))(Login);
