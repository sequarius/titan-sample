import { updateUser, removeUser, saveUser, listUser } from '@/services/user';
import { DEFAUNT_PAGE_SIZE } from '@/utils/constant';
const UserModel = {
  namespace: 'systemUser',
  state: {
    list: [],
    total: null,
    current: null,
    user: null,
  },
  effects: {
    *list({ payload: { page = 1 } }, { call, put }) {
      console.log(page);
      const response = yield call(listUser, {
        begin: (page - 1) * DEFAUNT_PAGE_SIZE,
        length: DEFAUNT_PAGE_SIZE,
      });
      console.log(response);
      if (response.result) {
        yield put({
          type: 'save',
          payload: {
            ...response.data,
          },
        });
      }
    },
    *saveUser({ payload: { user } }, { call, put, select }) {
      console.log(user);
      const response = yield call(saveUser, user);
      if (response.result) {
        const page = yield select(state => state.systemUser.current);
        yield put({ type: 'list', payload: { page } });
        yield put({
          type: 'setUser',
          payload: {
            user: null,
          },
        });
      }
    },
    *updateUser({ payload: { user } }, { call, put, select }) {
      console.log(user);
      const response = yield call(updateUser, user);
      if (response.result) {
        const page = yield select(state => state.systemUser.current);
        yield put({ type: 'list', payload: { page } });
        yield put({
          type: 'setUser',
          payload: {
            user: null,
          },
        });
      }
    },
    *removeUser({ payload: { id } }, { call, put, select }) {
      const response = yield call(removeUser, [id]);
      if (response.result) {
        const page = yield select(state => state.systemUser.current);
        yield put({ type: 'list', payload: { page } });
      }
    },
  },
  reducers: {
    save(state, { payload: { list, total, begin, length } }) {
      let current = begin / length + 1;
      return { ...state, list, total, current };
    },
    setUser(state, { payload: { user } }) {
      return { ...state, user };
    },
  },
  subscriptions: {
    setup({ dispatch, history }) {
      history.listen(({ pathname, query }) => {
        if (pathname === '/system/users') {
          dispatch({
            type: 'list',
            payload: query,
          });
        }
      });
    },
  },
};
export default UserModel;
