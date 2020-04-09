import request from '@/utils/request';

export async function listSysRole({ begin, length }) {
    return request(`/api/system/sysRoles?begin=${begin}&length=${length}`, { showMessage: false });
}

export async function saveSysRole(sysRole) {
    return request('/api/system/sysRole', { method: 'POST', data: sysRole });
}

export async function updateSysRole(sysRole) {
    return request('/api/system/sysRole', { method: 'PUT', data: sysRole });
}

export async function removeSysRole(ids) {
    return request('/api/system/sysRole', { method: 'DELETE', data: ids });
}

export async function getRoleTree(keyword) {
    return request(`/api/system/sysRoleTree?keyword=${keyword}`, { showMessage: false });
}