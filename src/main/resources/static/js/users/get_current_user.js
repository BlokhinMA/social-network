fetch('/api/users/get_current_user', {
    method: 'GET'
})
    .then(async response => {
        if (!response.ok) {
            const data = await response.json();
            throw new Error(`Ошибка ${response.status}: ${data.error}`);
        }
        return response.json();
    })
    .then(data => {
        const username = document.getElementById('username');
        username.textContent = data.login;
    });