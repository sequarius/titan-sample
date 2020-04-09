import { updateSysRole, removeSysRole, saveSysRole, listSysRole, getRoleTree } from '@/services/system/sysRole';
import { DEFAUNT_PAGE_SIZE } from '@/utils/constant';
const SysRoleModel = {
    namespace: 'systemSysRole',
    state: {
        list: [],
        total: null,
        current: null,
        sysRole: null,
        roleTree: [],
    },
    effects: {
        *list({ payload: { page = 1 } }, { call, put }) {
            const response = yield call(listSysRole, {
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
        *saveSysRole({ payload: { sysRole } }, { call, put, select }) {
            const response = yield call(saveSysRole, sysRole);
            if (response.result) {
                const page = yield select(state => state.systemSysRole.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysRole', payload: { sysRole: null } });
            }
        },
        *updateSysRole({ payload: { sysRole } }, { call, put, select }) {
            const response = yield call(updateSysRole, sysRole);
            if (response.result) {
                const page = yield select(state => state.systemSysRole.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysRole', payload: { sysRole: null } });
            }
        },
        *removeSysRole({ payload: { id } }, { call, put, select }) {
            const response = yield call(removeSysRole, [id]);
            if (response.result) {
                const page = yield select(state => state.systemSysRole.current);
                yield put({ type: 'list', payload: { page } });
            }
        },
        *getRoleTree({ payload: { keyword } }, { call, put }) {
            const response = yield call(getRoleTree, keyword);
            if (response.result) {
                yield put({ type: 'setRoleTree', payload: { roleTree: response.data} });
            }
        },
    },
    reducers: {
        save(state, { payload: { list, total, begin, length } }) {
            let current = begin / length + 1;
            return { ...state, list, total, current };
        },
        setSysRole(state, { payload: { sysRole } }) {
            return { ...state, sysRole };
        },
        setRoleTree(state,{ payload: {roleTree}}){
            return { ...state, roleTree };
        }
    },
    subscriptions: {
        setup({ dispatch, history }) {
            history.listen(({ pathname, query }) => {
                if (pathname === '/system/sysRoles') {
                    dispatch({ type: 'list', payload: query });
                }
            });
        },
    },
};
export default SysRoleModel;