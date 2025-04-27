const ownerId = window.location.pathname.split('/').pop();

fetch(`/api/albums/show_all/${ownerId}`, {
    method: 'GET'
})
    .then(async response => {

        const data = await response.json();

        const title = document.querySelector('title');
        const header = document.getElementById('header');
        const albumsDiv = document.getElementById('albums');

        if (response.ok) {

            let owner = data.owner;

            let textContent = `Альбомы пользователя ${owner.firstName} ${owner.lastName}`;
            title.textContent = textContent;
            header.textContent = textContent;

            let htmlCode = ``;

            let albums = data.albums;

            if (albums.length === 0) {
                htmlCode += '<p>Пользователь еще не добавил альбомы</p>';
            } else {
                albums.forEach((album) => {
                    htmlCode += `<p><a href="/album/${album.id}">${album.title}</a></p>`;
                });
            }

            albumsDiv.innerHTML = htmlCode;

        } else {
            let error = data.error;
            title.textContent = error;
            albumsDiv.textContent = error;
        }

    });