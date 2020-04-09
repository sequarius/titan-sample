import { LogoutOutlined, SettingOutlined, UserOutlined, KeyOutlined, SkinOutlined } from '@ant-design/icons';
import { Avatar, Menu, Modal } from 'antd';
import React from 'react';
import { connect } from 'dva';
import { router } from 'umi';
import HeaderDropdown from '../HeaderDropdown';
import { getCurrentUser } from '@/utils/authority';
import styles from './index.less';

const { SubMenu } = Menu;

const ColorList = ['#10069F96', '#FF5C3996', '#D9017A96', '#00AB5996',
  '#BB29BB96', '#B3692496', '#59CBE896', '#C4D60096',
  '#FFB81C96', '#003DA596', '#80E0A796', '#00627296',
  '#44D62C96', '#737B4C96',
];

class AvatarDropdown extends React.Component {
  onMenuClick = event => {
    const { key } = event;
    const { dispatch } = this.props;

    if (key === 'logout') {

      if (dispatch) {
        dispatch({
          type: 'common/logout',
        });
      }
      return;
    }
    if (key === 'changePassword') {
      if (dispatch) {
        dispatch({
          type: 'common/setPasswordModalShow',
          payload: true,
        });
      }
      return;
    }

    this.changeTheme(key)
  };

  changeTheme(key) {
    let styleLink = document.getElementById('theme-style');
    // let body = document.getElementsByTagName('body')[0];
    if (!styleLink) {
      styleLink = document.createElement('link');
      styleLink.type = 'text/css';
      styleLink.rel = 'stylesheet';
      styleLink.id = 'theme-style';
    }
    styleLink.href = `/theme/${key}.css`
    // body.className = 'body-wrap-theme1'
    document.body.append(styleLink);
    localStorage.setItem("theme", key);
  };

  componentDidMount() {
    let theme = localStorage.getItem("theme")
    if (theme) {
      this.changeTheme(theme);
    }
  }

  getAvatarDisplayName(name) {
    if (!name) {
      return '欢迎';
    }
    if (name.length < 3) {
      return name;
    }
    console.log(name.substr(length - 2));
    return name.substr(length - 2);
  }

  render() {
    const { menu } = this.props;
    const menuHeaderDropdown = (
      <Menu className={styles.menu} selectedKeys={[]} onClick={this.onMenuClick}>
        {menu && (
          <Menu.Item key="center">
            <UserOutlined />
            个人中心
          </Menu.Item>
        )}
        {menu && (
          <Menu.Item key="settings">
            <SettingOutlined />
            个人设置
          </Menu.Item>
        )}
        {menu && <Menu.Divider />}

        <Menu.Item key="changePassword">
          <KeyOutlined />
          修改密码
        </Menu.Item>
        <SubMenu title={<><SkinOutlined />切换主题</>}>
          <Menu.Item key="default">
            蓝色
        </Menu.Item>
          <Menu.Item key="red">
            红色
        </Menu.Item>
          <Menu.Item key="sunset">
            黄色
        </Menu.Item>
          <Menu.Item key="green">
            绿色
        </Menu.Item>
          <Menu.Item key="purple">
            紫色
        </Menu.Item>
        </SubMenu>

        <Menu.Item key="logout">
          <LogoutOutlined />
          退出登录
        </Menu.Item>
      </Menu>
    );
    return getCurrentUser() ? (
      <HeaderDropdown overlay={menuHeaderDropdown}>
        <span className={`${styles.action} ${styles.account}`}>
          <Avatar
            size="defualt"
            // className={styles.avatar}
            style={{ color: 'white',backgroundColor: ColorList[(getCurrentUser()?.id ?? 0) % ColorList.length], verticalAlign: 'middle' }}
            // src={getCurrentUser().avatar}
            alt="avatar"
          >
            {this.getAvatarDisplayName(getCurrentUser().name ?? getCurrentUser().username)}
          </Avatar>
          &nbsp;<span className={styles.name}>{getCurrentUser().name ?? getCurrentUser().username}</span>
        </span>
      </HeaderDropdown>
    ) : (
        <h1>尚未登陆</h1>
      );
  }
}

export default connect(({ common }) => ({
  common
}))(AvatarDropdown);
