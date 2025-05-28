const albumId = window.location.pathname.split('/').pop();
const albumHeader = document.getElementById("album-header");
const photosDiv = document.getElementById('photos');

let isOwner;

async function show() {

    const response = await fetch(`http://localhost:8081/api/v1/albums/show/${albumId}`, {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();

    isOwner = data.isOwner;

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
            let accessType = data.album.accessType;
            if (accessType === 'ALL') {
                htmlCode = `<p id="access-type">Тип доступа: для всех</p>`;
            } else {
                htmlCode = `<p id="access-type">Тип доступа: для друзей</p>`;
            }
            htmlCode += `<form id="change-access-type">
                                <p>Изменить тип доступа к альбому</p>                            
                                <input type="radio" id="all" name="accessType" value="ALL" required>
                                <label for="all">для всех</label>
                                <input type="radio" id="friends" name="accessType" value="FRIENDS">
                                <label for="friends">для друзей</label>
                                <input type="hidden" name="id" value="${albumId}">
                                <button>изменить тип доступа</button>
                        </form>
                        <form enctype="multipart/form-data" id="create-photos">
                            <label for="files">Добавить фотографии</label>
                            <input type="file" id="files" name="files" accept="image/*" multiple required>
                            <input type="hidden" name="albumId" value="${albumId}">
                            <button>добавить</button>
                        </form>`;
            albumHeader.insertAdjacentHTML('afterend', htmlCode);
        }

        htmlCode = ``;
        const photos = data.photos;

        if (photos.length === 0) {
            htmlCode += `<p id="no-photos">Фотографий нет</p>`;
        } else {
            photos.forEach((photo) => {
                htmlCode += `<a href="/photo/${photo}" class="reset">
                                 <img alt="" src="http://localhost:8081/api/v1/photos/show_entity/${photo}"
                                 class="pictures" />
                             </a>`;
            });
        }
    } else {
        htmlCode = `<p>${data.error}</p>`;
    }
    photosDiv.insertAdjacentHTML('afterbegin', htmlCode);

}

show();