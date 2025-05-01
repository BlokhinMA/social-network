const albumsDiv = document.getElementById("albums");
const form = document.getElementById('create-album');

form.addEventListener('submit', (e) => {
    e.preventDefault();

    const formData = new FormData(form);

    fetch(`http://localhost:8081/api/v1/albums/create`, {
        method: 'POST',
        credentials: 'include',
        body: formData
    })
        .then(async response => {
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
                    if (key === 'error') {
                        form.insertAdjacentHTML('beforeend', htmlCode);
                    } else {
                        if (key === `accessType`) {
                            document.getElementById(`radio-buttons`).insertAdjacentHTML('beforeend', htmlCode);
                        } else {
                            document.querySelector(`[name="${key}"]`).insertAdjacentHTML("afterend", htmlCode);
                        }
                    }
                }
            }
        });
});