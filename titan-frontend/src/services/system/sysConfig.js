import request from '@/utils/request';

export async function listSysConfig({ begin, length }) {
    return request(`/api/system/sysConfigs?begin=${begin}&length=${length}`, { showMessage: false });
}

export async function saveSysConfig(sysConfig) {
    return request('/api/system/sysConfig', { method: 'POST', data: sysConfig });
}

export async function updateSysConfig(sysConfig) {
    return request('/api/system/sysConfig', { method: 'PUT', data: sysConfig });
}

export async function removeSysConfig(ids) {
    return request('/api/system/sysConfig', { method: 'DELETE', data: ids });
}
