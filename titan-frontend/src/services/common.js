import request from '@/utils/request';
export async function login(params) {
  return request('/api/login', {
    method: 'POST',
    data: params,
  });
}

export async function logout() {
  return request('/api/logout', {
    method: 'POST',
  });
}

export async function changePassword(params) {
  return request('/api/password', {
    method: 'POST',
    data: params,
  });
}