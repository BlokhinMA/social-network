document.getElementById('find-friends').addEventListener('click', async () => {

    const keyword = document.getElementById('keyword');

    const response = await fetch(`http://localhost:8081/api/v1/friendships/find/${keyword.value}`, {
        method: 'GET',
        credentials: 'include'
    });

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