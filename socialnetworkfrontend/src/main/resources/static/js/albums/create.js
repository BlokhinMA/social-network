const albumsDiv = document.getElementById("albums");
const form = document.getElementById('create-album');

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(form);

    const response = await fetch(`http://localhost:8081/api/v1/albums/create`, {
        method: 'POST',
        credentials: 'include',
        body: formData
    });

    const errorElements = document.querySelectorAll('.error');
    if (errorElements.length) {
        errorElements.forEach((element) => {
            element.remove();
        });
    }
    const data = await response.json();
    let htmlCode;
    if (response.ok) {
        const noAlbumsP = document.getElementById("no-albums");
        if (noAlbumsP) {
            noAlbumsP.remove();
        }
        htmlCode = `<p>
                        <a href="/album/${data.id}">${data.title}</a>
                        <button class="delete-album" id="${data.id}">удалить</button>
                    </p>`;
        albumsDiv.insertAdjacentHTML("beforeend", htmlCode);
        form.reset();
    } else {
        for (const [key, value] of Object.entries(data)) {
            htmlCode = `<div class="error" style="color: red;">${value}</div>`;
            switch (key) {
                case 'error':
                    form.insertAdjacentHTML('beforeend', htmlCode);
                    break;
                case 'accessType':
                    document.getElementById(`radio-buttons`).insertAdjacentHTML('beforeend', htmlCode);
                    break;
                default:
                    document.querySelector(`[name="${key}"]`).insertAdjacentHTML("afterend", htmlCode);
            }
        }
    }

});