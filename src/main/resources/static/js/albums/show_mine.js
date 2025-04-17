fetch(`/api/albums/show_mine`, {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();

        let htmlCode = ``;
        if (response.ok) {
            data.forEach((album) => {
                htmlCode += `<p>
                                <a href="/album/${album.id}">${album.title}</a>
                                <button class="delete-button" id="${album.id}">удалить альбом</button>
                            </p>`;
            })
        } else {
            htmlCode += `<p>${data.error}</p>`;
        }
        albumsDiv.insertAdjacentHTML('afterbegin', htmlCode);
    });