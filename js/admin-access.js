function checkAdminAccess(userRoles) {
    return userRoles.includes('ROLE_ADMIN') || userRoles.includes('ROLE_PANEL_ADMIN');
}

function toggleAdminModule() {
    const adminModule = document.querySelector('.admin-module');
    if (adminModule) {
        adminModule.style.display = checkAdminAccess(userRoles) ? 'block' : 'none';
    }
}
