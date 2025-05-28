const userId = window.location.pathname.split('/').pop();

if (userId === localStorage.getItem("userId")) {
    window.location = '/my_friends';
}

const friendsDiv = document.getElementById('friends');

async function show() {
    const response = await fetch(`http://localhost:8081/api/v1/friendships/show/${userId}`, {
        method: 'GET',
        credentials: 'include'
    });

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

}

show();