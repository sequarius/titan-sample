import { updateSysUser, removeSysUser, saveSysUser, listSysUser } from '@/services/system/sysUser';
import { DEFAUNT_PAGE_SIZE } from '@/utils/constant';
const SysUserModel = {
    namespace: 'systemSysUser',
    state: {
        list: [],
        total: null,
        current: null,
        sysUser: null,
        keyword: null
    },
    effects: {
        *list({ payload: { page = 1 ,keyword,ids} }, { call, put }) {
            if(keyword){
                yield put({ type: 'setKeyword', payload: { keyword }});
            }

            const response = yield call(listSysUser, {
                begin: (page - 1) * DEFAUNT_PAGE_SIZE,
                length: DEFAUNT_PAGE_SIZE,
                keyword,
                ids
            });
            if (response.result) {
                yield put({
                    type: 'save',
                    payload: { ...response.data },
                });
            }
        },
        *saveSysUser({ payload: { sysUser } }, { call, put, select }) {
            const response = yield call(saveSysUser, sysUser);
            if (response.result) {
                const page = yield select(state => state.systemSysUser.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysUser', payload: { sysUser: null } });
            }
        },
        *updateSysUser({ payload: { sysUser } }, { call, put, select }) {
            const response = yield call(updateSysUser, sysUser);
            if (response.result) {
                const page = yield select(state => state.systemSysUser.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysUser', payload: { sysUser: null } });
            }
        },
        *removeSysUser({ payload: { id } }, { call, put, select }) {
            const response = yield call(removeSysUser, [id]);
            if (response.result) {
                const page = yield select(state => state.systemSysUser.current);
                yield put({ type: 'list', payload: { page } });
            }
        },
    },
    reducers: {
        save(state, { payload: { list, total, begin, length } }) {
            let current = begin / length + 1;
            return { ...state, list, total, current };
        },
        setSysUser(state, { payload: { sysUser } }) {
            return { ...state, sysUser };
        },
        setKeyword(state, { payload: { keyword } }) {
            return { ...state, keyword };
        }
    },
    subscriptions: {
        setup({ dispatch, history }) {
            history.listen(({ pathname, query }) => {
                if (pathname === '/system/sysUsers') {
                    dispatch({ type: 'list', payload: query });
                }
            });
        },
    },
};
export default SysUserModel;