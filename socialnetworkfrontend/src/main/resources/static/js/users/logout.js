const logoutButton = document.querySelector('body').querySelector('#logout');
logoutButton.addEventListener('click', async () => {
    const response = await fetch('http://localhost:8081/api/v1/auth/logout', {
        credentials: 'include'
    });
    if (response.ok) {
        localStorage.removeItem('userId');
        window.location = '/sign_in';
    }
});