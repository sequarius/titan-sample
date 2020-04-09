import request from '@/utils/request';

export async function listSysPermission({ begin, length }) {
    return request(`/api/system/sysPermissions?begin=${begin}&length=${length}`, { showMessage: false });
}

export async function saveSysPermission(sysPermission) {
    return request('/api/system/sysPermission', { method: 'POST', data: sysPermission });
}

export async function updateSysPermission(sysPermission) {
    return request('/api/system/sysPermission', { method: 'PUT', data: sysPermission });
}

export async function removeSysPermission(ids) {
    return request('/api/system/sysPermission', { method: 'DELETE', data: ids });
}

export async function getPermissionTree(keyword) {
    return request(`/api/system/permissionTree?keyword=${keyword}`, { showMessage: false });
}