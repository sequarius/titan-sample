import request from '@/utils/request';

export async function listSysMetadata({ begin, length, groupId }) {
    return request(`/api/system/sysMetadatas?begin=${begin}&length=${length}&groupId=${groupId}`, { showMessage: false });
}

export async function listSysMetadataSource({ keyword, groupkey }) {
    return request(`/api/system/sysMetadataSource?groupKey=${groupkey}&keyword=${keyword}`, { showMessage: false });
}

export async function saveSysMetadata(sysMetadata) {
    return request('/api/system/sysMetadata', { method: 'POST', data: sysMetadata });
}

export async function updateSysMetadata(sysMetadata) {
    return request('/api/system/sysMetadata', { method: 'PUT', data: sysMetadata });
}

export async function removeSysMetadata(ids) {
    return request('/api/system/sysMetadata', { method: 'DELETE', data: ids });
}
