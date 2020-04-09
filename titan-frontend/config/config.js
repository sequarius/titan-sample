import defaultSettings from './defaultSettings'; // https://umijs.org/config/

import slash from 'slash2';
import themePluginConfig from './themePluginConfig';
const { pwa } = defaultSettings; // preview.pro.ant.design only do not use in your production ;
// preview.pro.ant.design 专用环境变量，请不要在你的项目中使用它。

const { ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION, ERP_ENV } = process.env;
const isAntDesignProPreview = ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION === 'site';
const plugins = [
  ['umi-plugin-antd-icon-config', {}],
  [
    'umi-plugin-react',
    {
      antd: true,
      dva: {
        hmr: true,
        config: {
          onError(e) {
            e.preventDefault();
            console.error(e);
          },
        },
      },
      locale: {
        // // default false
        // enable: true,
        // // default zh-CN
        // default: 'zh-CN',
        // // default true, when it is true, will use `navigator.language` overwrite default
        // baseNavigator: true,
      },
      dynamicImport: {
        loadingComponent: './components/PageLoading/index',
        webpackChunkName: true,
        level: 3,
      },
      pwa: pwa
        ? {
            workboxPluginMode: 'InjectManifest',
            workboxOptions: {
              importWorkboxFrom: 'local',
            },
          }
        : false, // default close dll, because issue https://github.com/ant-design/ant-design-pro/issues/4665
      // dll features https://webpack.js.org/plugins/dll-plugin/
      // dll: {
      //   include: ['dva', 'dva/router', 'dva/saga', 'dva/fetch'],
      //   exclude: ['@babel/runtime', 'netlify-lambda'],
      // },
    },
  ],
  [
    'umi-plugin-pro-block',
    {
      moveMock: false,
      moveService: false,
      modifyRequest: true,
      autoAddMenu: true,
    },
  ],
];

if (isAntDesignProPreview) {
  // 针对 preview.pro.ant.design 的 GA 统计代码
  plugins.push([
    'umi-plugin-ga',
    {
      code: 'UA-72788897-6',
    },
  ]);
  plugins.push(['umi-plugin-antd-theme', themePluginConfig]);
}

if (ERP_ENV) {
  plugins.push(['umi-plugin-antd-theme', themePluginConfig]);
}

export default {
  plugins,
  hash: true,
  targets: {
    ie: 11,
  },
  // umi routes: https://umijs.org/zh/guide/router.html
  routes: [
    {
      path: '/login',
      component: '../layouts/UserLayout',
      routes: [
        {
          name: 'login',
          path: '/login',
          component: './system/Login',
        },
      ],
    },
    {
      path: '/',
      component: '../layouts/SecurityLayout',
      routes: [
        {
          path: '/',
          component: '../layouts/BasicLayout',
          authority: ['admin', 'user'],
          routes: [
            {
              path: '/',
              redirect: '/welcome',
            },
            {
              path: '/welcome',
              name: 'welcome',
              icon: 'smile',
              component: './Welcome',
            },
            {
              path: '/admin',
              name: 'admin',
              icon: 'crown',
              component: './Admin',
              authority: ['admin'],
            },
            {
              name: '系统管理',
              icon: 'DesktopOutlined',
              path: '/system',
              // authority: [
              //   // 'system:sysUser:view',
              //   // 'system:sysRole:view',
              //   // 'system:sysPermission:view',
              // ],
              routes: [
                {
                  name: '用户管理',
                  icon: 'UserOutlined',
                  path: '/system/sysUsers',
                  // authority: ['system:sysUser:view'],
                  component: './system/SysUser',
                },
                {
                  name: '角色管理',
                  icon: 'TeamOutlined',
                  path: '/system/sysRoles',
                  // authority: ['system:sysRole:view'],
                  component: './system/SysRole',
                },
                {
                  name: '权限管理',
                  icon: 'KeyOutlined',
                  path: '/system/sysPermissions',
                  authority: ['system:sysPermission:view'],
                  component: './system/SysPermission',
                },
                {
                  name: '元数据组管理',
                  icon: 'AppstoreOutlined',
                  path: '/system/sysMetadataGroups',
                  authority: ['system:sysMetadataGroup:view'],
                  component: './system/SysMetadataGroup',
                },
                {
                  name: '元数据管理',
                  hideInMenu: true,
                  path: '/system/sysMetadataGroups/:id',
                  authority: ['system:sysMetadata:view'],
                  component: './system/SysMetadata',
                },
                {
                  name: '配置管理',
                  icon: 'SettingOutlined',
                  path: '/system/sysConfigs',
                  authority: ['system:sysConfig:view'],
                  component: './system/SysConfig',
                },
              ],
            },
            {
              component: './404',
            },
          ],
        },
        {
          component: './404',
        },
      ],
    },
    {
      component: './404',
    },
  ],
  // Theme for antd: https://ant.design/docs/react/customize-theme-cn
  theme: {
    // ...darkTheme,
  },
  define: {
    ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION:
      ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION || '', // preview.pro.ant.design only do not use in your production ; preview.pro.ant.design 专用环境变量，请不要在你的项目中使用它。
  },
  ignoreMomentLocale: true,
  lessLoaderOptions: {
    javascriptEnabled: true,
  },
  disableRedirectHoist: true,
  cssLoaderOptions: {
    modules: true,
    getLocalIdent: (context, _, localName) => {
      if (
        context.resourcePath.includes('node_modules') ||
        context.resourcePath.includes('ant.design.pro.less') ||
        context.resourcePath.includes('global.less')
      ) {
        return localName;
      }

      const match = context.resourcePath.match(/src(.*)/);

      if (match && match[1]) {
        const antdProPath = match[1].replace('.less', '');
        const arr = slash(antdProPath)
          .split('/')
          .map(a => a.replace(/([A-Z])/g, '-$1'))
          .map(a => a.toLowerCase());
        return `antd-pro${arr.join('-')}-${localName}`.replace(/--/g, '-');
      }

      return localName;
    },
  },
  manifest: {
    basePath: '/',
  },
  // chainWebpack: webpackPlugin,
  proxy: {
    '/api/': {
      target: 'http://localhost:8080/',
      changeOrigin: true,
      pathRewrite: {
        '^/api': '',
      },
    },
  },
};
