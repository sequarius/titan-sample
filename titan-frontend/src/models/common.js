import { router } from 'umi';
import { login, logout,changePassword } from '@/services/common';
import { setAuthority, cancelAuthority, getCurrentUser } from '@/utils/authority';
import { getPageQuery } from '@/utils/utils';
import { stringify } from 'querystring';

const Model = {
  namespace: 'common',
  state: {
    status: undefined,
    passwordModalShow: false
  },
  effects: {
    *login({ payload }, { call, put }) {
      const response = yield call(login, payload);

      if (response.result) {
        yield put({
          type: 'changeLoginStatus',
          payload: response.data,
        }); // Login successfully
        const urlParams = new URL(window.location.href);
        const params = getPageQuery();
        let { redirect } = params;

        if (redirect) {
          const redirectUrlParams = new URL(redirect);

          if (redirectUrlParams.origin === urlParams.origin) {
            redirect = redirect.substr(urlParams.origin.length);

            if (redirect.match(/^\/.*#/)) {
              redirect = redirect.substr(redirect.indexOf('#') + 1);
            }
          } else {
            window.location.href = '/';
            return;
          }
        }
        router.replace(redirect || '/');
      }
    },

    *logout(_, { call }) {
      const response = yield call(logout);
      if (!response.result) {
        return;
      }
      const { redirect } = getPageQuery(); // Note: There may be security issues, please note
      cancelAuthority();
      if (window.location.pathname !== '/login' && !redirect) {
        let redirect = stringify({message: '您已安全退出！'});
        window.location.href=`/login?${redirect}`
      }
    },

    *changePassword({ payload:{password} }, { call, put }) {
      const response = yield call(changePassword, password);
      if (response.result) {
        yield put({
          type: 'setPasswordModalShow',
          payload: false
        })
        yield put({
          type: 'logout',
        }); 
      }
    },

  },
  reducers: {
    setPasswordModalShow(state, {payload:passwordModalShow}){
      return {...state,passwordModalShow};
    },
    changeLoginStatus(state, { payload }) {
      setAuthority(payload);
      return { ...state, status: payload.status, type: payload.type };
    },
  },
  subscriptions: {
    setup({ history }) {
      history.listen(({ pathname }) => {
        if (pathname === '/login' && getCurrentUser() !== null) {
          router.replace('/');
        }
      });
    },
  },
};
export default Model;
