fetch('/api/users/get_current_user', {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();
        if (response.ok) {
            const username = document.getElementById('username');
            username.textContent = data.login;
        } else {
            console.log(data.error);
        }
    });