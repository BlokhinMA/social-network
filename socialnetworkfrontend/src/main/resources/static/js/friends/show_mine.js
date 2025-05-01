if (localStorage.getItem('userId') === null) {
    window.location = '/sign_in';
}

const myFriendsDiv = document.getElementById('my-friends');

fetch('http://localhost:8081/api/v1/friendships/show_mine', {
    method: 'GET',
    credentials: 'include'
})
    .then(async response => {

        const data = await response.json();

        let htmlCode;

        if (response.ok) {

            if (data.length === 0) {
                htmlCode = `<p id="no-friends">Вы еще не добавили друзей</p>`;
            } else {
                let id;
                htmlCode = ``;
                data.forEach(friend => {
                    id = friend.id;
                    htmlCode += `<p>
                                     <a href="/profile/${id}">${friend.firstName} ${friend.lastName}</a>
                                     <button class="delete-friend" id="${id}">удалить</button>
                                 </p>`;
                });
            }

        } else {
            htmlCode = `<p>${data.error}</p>`;
        }

        myFriendsDiv.insertAdjacentHTML('afterbegin', htmlCode);

    });