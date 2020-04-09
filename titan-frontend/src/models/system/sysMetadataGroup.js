import { updateSysMetadataGroup, removeSysMetadataGroup, saveSysMetadataGroup, listSysMetadataGroup } from '@/services/system/sysMetadataGroup';
import { DEFAUNT_PAGE_SIZE } from '@/utils/constant';
const SysMetadataGroupModel = {
    namespace: 'systemSysMetadataGroup',
    state: {
        list: [],
        total: null,
        current: null,
        sysMetadataGroup: null
    },
    effects: {
        *list({ payload: { page = 1 } }, { call, put }) {
            const response = yield call(listSysMetadataGroup, {
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
        *saveSysMetadataGroup({ payload: { sysMetadataGroup } }, { call, put, select }) {
            const response = yield call(saveSysMetadataGroup, sysMetadataGroup);
            if (response.result) {
                const page = yield select(state => state.systemSysMetadataGroup.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysMetadataGroup', payload: { sysMetadataGroup: null } });
            }
        },
        *updateSysMetadataGroup({ payload: { sysMetadataGroup } }, { call, put, select }) {
            const response = yield call(updateSysMetadataGroup, sysMetadataGroup);
            if (response.result) {
                const page = yield select(state => state.systemSysMetadataGroup.current);
                yield put({ type: 'list', payload: { page } });
                yield put({ type: 'setSysMetadataGroup', payload: { sysMetadataGroup: null } });
            }
        },
        *removeSysMetadataGroup({ payload: { id } }, { call, put, select }) {
            const response = yield call(removeSysMetadataGroup, [id]);
            if (response.result) {
                const page = yield select(state => state.systemSysMetadataGroup.current);
                yield put({ type: 'list', payload: { page } });
            }
        },
    },
    reducers: {
        save(state, { payload: { list, total, begin, length } }) {
            let current = begin / length + 1;
            return { ...state, list, total, current };
        },
        setSysMetadataGroup(state, { payload: { sysMetadataGroup } }) {
            return { ...state, sysMetadataGroup };
        }
    },
    subscriptions: {
        setup({ dispatch, history }) {
            history.listen(({ pathname, query }) => {
                if (pathname === '/system/sysMetadataGroups') {
                    dispatch({ type: 'list', payload: query });
                }
            });
        },
    },
};
export default SysMetadataGroupModel;