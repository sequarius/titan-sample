import { updateSysMetadata, removeSysMetadata, saveSysMetadata, listSysMetadata } from '@/services/system/sysMetadata';
import { getSysMetadataGroup } from '@/services/system/sysMetadataGroup';
import { DEFAUNT_PAGE_SIZE } from '@/utils/constant';
import pathToRegexp from 'path-to-regexp';
import { notification } from 'antd';

const SysMetadataModel = {
    namespace: 'systemSysMetadata',
    state: {
        list: [],
        total: null,
        current: null,
        sysMetadata: null,
        groupId: null,
        sysMetadataGroup: null,
    },
    effects: {
        *list({ payload: { page = 1, groupId } }, { call, put }) {
            const response = yield call(listSysMetadata, {
                begin: (page - 1) * DEFAUNT_PAGE_SIZE,
                length: DEFAUNT_PAGE_SIZE,
                groupId
            });
            const groupResponse = yield call(getSysMetadataGroup, groupId);
            if (response.result && groupResponse.result) {
                yield put({
                    type: 'save',
                    payload: { ...response.data, sysMetadataGroup: groupResponse.data },
                });
            } else {
                notification.warn({
                    description: '获取元数据失败，请刷新重试！',
                    message: '请刷新重试',
                });
            }
        },
        *saveSysMetadata({ payload: { sysMetadata } }, { call, put, select }) {
            const response = yield call(saveSysMetadata, sysMetadata);
            if (response.result) {
                const page = yield select(state => state.systemSysMetadata.current);
                const groupId = sysMetadata.groupId;
                yield put({ type: 'list', payload: { page, groupId } });
                yield put({ type: 'setSysMetadata', payload: { sysMetadata: null } });
            }
        },
        *updateSysMetadata({ payload: { sysMetadata } }, { call, put, select }) {
            const response = yield call(updateSysMetadata, sysMetadata);
            if (response.result) {
                const page = yield select(state => state.systemSysMetadata.current);
                const groupId = sysMetadata.groupId;
                yield put({ type: 'list', payload: { page, groupId } });
                yield put({ type: 'setSysMetadata', payload: { sysMetadata: null } });
            }
        },
        *removeSysMetadata({ payload: { id } }, { call, put, select }) {
            const response = yield call(removeSysMetadata, [id]);
            if (response.result) {
                const page = yield select(state => state.systemSysMetadata.current);
                const groupId = yield select(state => state.systemSysMetadata.sysMetadataGroup.id);
                yield put({ type: 'list', payload: { page, groupId } });
            }
        },
    },
    reducers: {
        save(state, { payload: { length, begin }, payload }) {
            let current = begin / length + 1;
            return { ...state, current, ...payload };
        },
        setSysMetadata(state, { payload: { sysMetadata } }) {
            return { ...state, sysMetadata };
        }
    },
    subscriptions: {
        setup({ dispatch, history }) {
            history.listen(({ pathname, query }) => {
                const match = pathToRegexp('/system/sysMetadataGroups/:id').exec(pathname);
                if (match) {
                    // 清理之前页面显示的其它元数据
                    dispatch({ type: 'save', payload: { length: 0, begin: 0, list: [] } });

                    const groupId = match[1];
                    query = { ...query, groupId }
                    dispatch({ type: 'list', payload: query });
                }
            });
        },
    },
};
export default SysMetadataModel;