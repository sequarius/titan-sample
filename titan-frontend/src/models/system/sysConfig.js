import { updateSysConfig, removeSysConfig, saveSysConfig, listSysConfig } from '@/services/system/sysConfig';
import { DEFAUNT_PAGE_SIZE } from '@/utils/constant';
const SysConfigModel = {
    namespace: 'systemSysConfig',
    state: {
        list: [],
        total: null,
        current: null,
        sysConfig: null
    },
    effects: {
        *list({ payload: { page = 1 } }, { call, put }) {
            const response = yield call(listSysConfig, {
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
        *saveSysConfig({ payload: { sysConfig } }, { call, put, select }) {
            const response = yield call(saveSysConfig, sysConfig);
            if (response.result) {
                const page = yield select(state => state.systemSysConfig.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysConfig', payload: { sysConfig: null } });
            }
        },
        *updateSysConfig({ payload: { sysConfig } }, { call, put, select }) {
            const response = yield call(updateSysConfig, sysConfig);
            if (response.result) {
                const page = yield select(state => state.systemSysConfig.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysConfig', payload: { sysConfig: null } });
            }
        },
        *removeSysConfig({ payload: { id } }, { call, put, select }) {
            const response = yield call(removeSysConfig, [id]);
            if (response.result) {
                const page = yield select(state => state.systemSysConfig.current);
                yield put({ type: 'list', payload: { page } });
            }
        },
    },
    reducers: {
        save(state, { payload: { list, total, begin, length } }) {
            let current = begin / length + 1;
            return { ...state, list, total, current };
        },
        setSysConfig(state, { payload: { sysConfig } }) {
            return { ...state, sysConfig };
        }
    },
    subscriptions: {
        setup({ dispatch, history }) {
            history.listen(({ pathname, query }) => {
                if (pathname === '/system/sysConfigs') {
                    dispatch({ type: 'list', payload: query });
                }
            });
        },
    },
};
export default SysConfigModel;