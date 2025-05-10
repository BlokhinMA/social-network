const button = document.getElementById('button');

button.addEventListener('click', async () => {
    const keyword = document.getElementById('keyword').value;

    const response = await fetch(`http://localhost:8081/api/v1/albums/find/${keyword}`, {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();
    let htmlCode = ``;
    if (response.ok) {
        const albums = document.getElementById('albums');

        if (data.length === 0) {
            htmlCode += `<p>Ничего не найдено</p>`;
        } else {
            data.forEach(album => {
                htmlCode += `<p><a href="/album/${album.id}">${album.title}</a></p>`;
            });
        }

        albums.innerHTML = htmlCode;
    } else {
        htmlCode += `<span>${data.error}</span>`;
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});