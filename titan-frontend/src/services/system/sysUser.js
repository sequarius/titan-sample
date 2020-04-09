import request from '@/utils/request';

export async function listSysUser({ begin, length, keyword, ids }) {
    return request(`/api/system/sysUsers?begin=${begin}&length=${length}&keyword=${keyword??''}&ids=${ids??''}`, { showMessage: false });
}

export async function saveSysUser(sysUser) {
    return request('/api/system/sysUser', { method: 'POST', data: sysUser });
}

export async function updateSysUser(sysUser) {
    return request('/api/system/sysUser', { method: 'PUT', data: sysUser });
}

export async function removeSysUser(ids) {
    return request('/api/system/sysUser', { method: 'DELETE', data: ids });
}
