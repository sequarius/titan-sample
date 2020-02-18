import request from '@/utils/request';
export async function query() {
  return request('/api/users');
}
export async function queryCurrent() {
  return request('/api/currentUser');
}
export async function queryNotices() {
  return request('/api/notices');
}

export async function listUser({ begin, length }) {
  return request(`/api/system/users?begin=${begin}&length=${length}`, { showMessage: false });
}

export async function saveUser(user) {
  return request('/api/system/user', { method: 'POST', data: user });
}

export async function updateUser(user) {
  return request('/api/system/user', { method: 'PUT', data: user });
}

export async function removeUser(ids) {
  return request('/api/system/user', { method: 'DELETE', data: ids });
}
