import { updateSysU2, removeSysU2, saveSysU2, listSysU2 } from '@/services/system/sysU2';
import { DEFAUNT_PAGE_SIZE } from '@/utils/constant';
const SysU2Model = {
  namespace: 'systemSysU2',
  state: {
    list: [],
    total: null,
    current: null,
    sysU2: null,
  },
  effects: {
    *list({ payload: { page = 1 } }, { call, put }) {
      const response = yield call(listSysU2, {
        begin: (page - 1) * DEFAUNT_PAGE_SIZE,
        length: DEFAUNT_PAGE_SIZE,
      });
      if (response.result) {
        yield put({
          type: 'save',
          payload: { ...response.data },
        });
      }
    },
    *saveSysU2({ payload: { sysU2 } }, { call, put, select }) {
      const response = yield call(saveSysU2, sysU2);
      if (response.result) {
        const page = yield select(state => state.systemSysU2.current);
        yield put({ type: 'list', payload: { page } });
        yield put({ type: 'setSysU2', payload: { sysU2: null } });
      }
    },
    *updateSysU2({ payload: { sysU2 } }, { call, put, select }) {
      const response = yield call(updateSysU2, sysU2);
      if (response.result) {
        const page = yield select(state => state.systemSysU2.current);
        yield put({ type: 'list', payload: { page } });
        yield put({ type: 'setSysU2', payload: { sysU2: null } });
      }
    },
    *removeSysU2({ payload: { id } }, { call, put, select }) {
      const response = yield call(removeSysU2, [id]);
      if (response.result) {
        const page = yield select(state => state.systemSysU2.current);
        yield put({ type: 'list', payload: { page } });
      }
    },
  },
  reducers: {
    save(state, { payload: { list, total, begin, length } }) {
      let current = begin / length + 1;
      return { ...state, list, total, current };
    },
    setSysU2(state, { payload: { sysU2 } }) {
      return { ...state, sysU2 };
    },
  },
  subscriptions: {
    setup({ dispatch, history }) {
      history.listen(({ pathname, query }) => {
        if (pathname === '/system/sysU2s') {
          dispatch({ type: 'list', payload: query });
        }
      });
    },
  },
};
export default SysU2Model;
