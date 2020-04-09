import { updateSysPermission, removeSysPermission, saveSysPermission, listSysPermission,getPermissionTree } from '@/services/system/sysPermission';
import { DEFAUNT_PAGE_SIZE } from '@/utils/constant';
const SysPermissionModel = {
    namespace: 'systemSysPermission',
    state: {
        list: [],
        total: null,
        current: null,
        sysPermission: null,
        permissionTree: []
    },
    effects: {
        *list({ payload: { page = 1 } }, { call, put }) {
            const response = yield call(listSysPermission, {
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
        *saveSysPermission({ payload: { sysPermission } }, { call, put, select }) {
            const response = yield call(saveSysPermission, sysPermission);
            if (response.result) {
                const page = yield select(state => state.systemSysPermission.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysPermission', payload: { sysPermission: null } });
            }
        },
        *updateSysPermission({ payload: { sysPermission } }, { call, put, select }) {
            const response = yield call(updateSysPermission, sysPermission);
            if (response.result) {
                const page = yield select(state => state.systemSysPermission.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysPermission', payload: { sysPermission: null } });
            }
        },
        *removeSysPermission({ payload: { id } }, { call, put, select }) {
            const response = yield call(removeSysPermission, [id]);
            if (response.result) {
                const page = yield select(state => state.systemSysPermission.current);
                yield put({ type: 'list', payload: { page } });
            }
        },
        *getPermissionTree({ payload: { keyword } }, { call, put }) {
            const response = yield call(getPermissionTree, keyword);
            if (response.result) {
                yield put({ type: 'setPermissionTree', payload: { permissionTree: response.data} });
            }
        },
    },
    reducers: {
        save(state, { payload: { list, total, begin, length } }) {
            let current = begin / length + 1;
            return { ...state, list, total, current };
        },
        setSysPermission(state, { payload: { sysPermission } }) {
            return { ...state, sysPermission };
        },
        setPermissionTree(state,{ payload: {permissionTree}}){
            return { ...state, permissionTree };
        }
    },
    subscriptions: {
        setup({ dispatch, history }) {
            history.listen(({ pathname, query }) => {
                if (pathname === '/system/sysPermissions') {
                    dispatch({ type: 'list', payload: query });
                }
            });
        },
    },
};
export default SysPermissionModel;