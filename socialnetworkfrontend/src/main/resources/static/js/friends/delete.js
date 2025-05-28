document.addEventListener('click', async (e) => {

    const button = e.target;

    if (!button?.classList.contains('delete-friend')) return;

    const response = await fetch(`http://localhost:8081/api/v1/friendships/delete/${button.id}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    let errorElement = document.getElementById("error");
    if (errorElement) {
        errorElement.remove();
    }

    let htmlCode;

    if (response.ok) {
        button.parentElement.remove();

        if (myFriendsDiv.children.length === 0) {
            htmlCode = '<p id="no-friends">Вы еще не добавили друзей</p>';
            document.getElementById('my-friends').insertAdjacentHTML('afterbegin', htmlCode);
        }

    } else {
        const data = await response.json();
        htmlCode = `<span style="color: red;">${data.error}</span>`;
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});