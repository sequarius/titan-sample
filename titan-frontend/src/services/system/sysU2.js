import request from '@/utils/request';

export async function listSysU2({ begin, length }) {
  return request(`/api/system/sysU2s?begin=${begin}&length=${length}`, { showMessage: false });
}

export async function saveSysU2(sysU2) {
  return request('/api/system/sysU2', { method: 'POST', data: sysU2 });
}

export async function updateSysU2(sysU2) {
  return request('/api/system/sysU2', { method: 'PUT', data: sysU2 });
}

export async function removeSysU2(ids) {
  return request('/api/system/sysU2', { method: 'DELETE', data: ids });
}
