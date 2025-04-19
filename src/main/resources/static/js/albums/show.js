const albumId = window.location.pathname.split('/').pop();
const albumHeader = document.getElementById("album-header");
const photosDiv = document.getElementById('photos');

let isOwner;

fetch(`/api/albums/show/${albumId}`, {
    method: 'GET'
})
    .then(async (response) => {
        const data = await response.json();

        isOwner = data.owner;

        let htmlCode = ``;
        if (response.ok) {

            const title = data.album.title;

            document.querySelector(`title`).textContent = title;

            htmlCode += `<h1 class="header">Альбом ${title}</h1>`;
            if (isOwner) {
                htmlCode += `<button id="delete-album-button">удалить</button>`;
            }
            albumHeader.insertAdjacentHTML('afterbegin', htmlCode);

            if (isOwner) {
                htmlCode = `<form enctype="multipart/form-data" id="create-photos">
                                <label for="files">Добавить фотографии</label>
                                <input type="file" id="files" name="files" accept="image/*" multiple>
                                <button>добавить</button>
                            </form>`;
                albumHeader.insertAdjacentHTML('afterend', htmlCode);
            }

            htmlCode = ``;
            data.photos.forEach((photo) => {
                htmlCode += `<p>
                                 <a href="/photo/${photo}" class="reset">
                                     <img alt="" src="/api/photos/show_entity/${photo}" class="pictures" />
                                 </a>
                             </p>`;
            });
        } else {
            htmlCode = `<p>${data.error}</p>`;
        }
        photosDiv.insertAdjacentHTML('afterbegin', htmlCode);
    });