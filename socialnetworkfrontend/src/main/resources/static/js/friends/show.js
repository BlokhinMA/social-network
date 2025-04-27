const userId = window.location.pathname.split('/').pop();
const friendsDiv = document.getElementById('friends');

fetch(`/api/friendships/show/${userId}`, {
    method: 'GET'
})
    .then(async response => {

        const data = await response.json();

        let htmlCode;

        if (response.ok) {

            const user = data.user;

            let textContent = `Друзья пользователя ${user.firstName} ${user.lastName}`;

            document.querySelector('title').textContent = textContent;

            document.getElementById('header').textContent = textContent;

            const friends = data.friends;

            if (friends.length === 0) {
                htmlCode = `<p>Пользователь еще не добавил друзей</p>`;
            } else {
                htmlCode = ``;
                friends.forEach(friend => {
                    htmlCode += `<p>
                                     <a href="/profile/${friend.id}">${friend.firstName} ${friend.lastName}</a>
                                 </p>`;
                });
            }

        } else {
            htmlCode = `<p>${data.error}</p>`;
        }

        friendsDiv.insertAdjacentHTML('afterbegin', htmlCode);

    });