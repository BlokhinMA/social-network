const userId = window.location.pathname.split('/').pop();

fetch(`/api/users/get_user/${userId}`, {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();

        let htmlCode = '';
        const title = document.querySelector('title');
        const header = document.querySelector('header');
        if (response.ok) {
            title.textContent = `${data.firstName} ${data.lastName}`;
            htmlCode += `<h1>${data.firstName} ${data.lastName}</h1>
                         <p><a href="/friends/${data.id}">Друзья пользователя</a></p>
                         <p><a href="/albums/${data.id}">Альбомы пользователя</a></p>
                         <p><a href="/communities/${data.id}">Сообщества пользователя</a></p>
                         <br>
                         <p><a href="/message/${data.id}">Написать сообщение</a></p>`;
            header.insertAdjacentHTML('afterend', htmlCode);
        } else {
            title.textContent = `${data.error}`;
            htmlCode += `<p>${data.error}</p>`;
            header.insertAdjacentHTML('afterend', htmlCode);
        }
    });