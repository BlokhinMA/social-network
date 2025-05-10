async function showMine() {
    const response = await fetch(`http://localhost:8081/api/v1/albums/show_mine`, {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();

    let htmlCode = ``;
    if (response.ok) {
        if (data.length === 0) {
            htmlCode += `<p id="no-albums">Альбомы пока не добавлены</p>`;
        } else {
            data.forEach((album) => {
                htmlCode += `<p>
                                 <a href="/album/${album.id}">${album.title}</a>
                                 <button class="delete-album" id="${album.id}">удалить альбом</button>
                             </p>`;
            });
        }
    } else {
        htmlCode += `<p>${data.error}</p>`;
    }
    albumsDiv.insertAdjacentHTML('afterbegin', htmlCode);

}

showMine();