import { reloadAuthorized } from './Authorized'; // use localStorage to store the authority info, which might be sent from server in actual project.

export function getAuthority() {
  return getCurrentUser()?.permissions ?? [];
}

export function getCurrentUser() {
  try {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    return currentUser;
  } catch (e) {
    console.warn(e);
    return null;
  }
}

export function setAuthority(currentUser) {
  localStorage.setItem('currentUser', JSON.stringify(currentUser)); // auto reload
  reloadAuthorized();
}

export function cancelAuthority() {
  localStorage.removeItem('currentUser');
  sessionStorage.clear();
}
