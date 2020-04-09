import request from '@/utils/request';

export async function getSysMetadataGroup(id) {
    return request(`/api/system/sysMetadataGroup/${id}`, { showMessage: false });
}

export async function listSysMetadataGroup({ begin, length }) {
    return request(`/api/system/sysMetadataGroups?begin=${begin}&length=${length}`, { showMessage: false });
}

export async function saveSysMetadataGroup(sysMetadataGroup) {
    return request('/api/system/sysMetadataGroup', { method: 'POST', data: sysMetadataGroup });
}

export async function updateSysMetadataGroup(sysMetadataGroup) {
    return request('/api/system/sysMetadataGroup', { method: 'PUT', data: sysMetadataGroup });
}

export async function removeSysMetadataGroup(ids) {
    return request('/api/system/sysMetadataGroup', { method: 'DELETE', data: ids });
}
