const button = document.getElementById('find-friends');

button.addEventListener('click', () => {

    const keyword = document.getElementById('keyword');

    fetch(`http://localhost:8081/api/v1/friendships/find/${keyword.value}`, {
        method: 'GET',
        credentials: 'include'
    })
        .then(async response => {

            const data = await response.json();

            let htmlCode;

            if (response.ok) {

                htmlCode = '';
                let id;

                data.forEach((friend) => {
                    id = friend.id;
                    htmlCode += `<p>
                                     <a href="/profile/${id}">${friend.firstName} ${friend.lastName}</a>
                                     <button class="make-friend" id="${id}">добавить</button>
                                 </p>`;
                });

            } else {
                htmlCode = `<p>${data.error}</p>`;
            }

            document.getElementById('possible-friends').innerHTML = htmlCode;

        });

});