const photoId = window.location.pathname.split('/').pop();

const photoDiv = document.getElementById('photo');
const tagsDiv = document.getElementById('tags');
const ratingsDiv = document.getElementById('ratings');
const commentsDiv = document.getElementById('comments');

let isOwner;

async function show() {
    const response = await fetch(`http://localhost:8081/api/v1/photos/show/${photoId}`, {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();

    if (response.ok) {
        isOwner = data.isOwner;

        renderPhoto(data, isOwner);

        renderTags(data, isOwner);

        renderRatings(data);

        renderComments(data);

    } else {
        renderError(data);
    }

}

function renderPhoto(data, isOwner) {
    const photo = data.photo;
    let htmlCode = `<img alt="" src="http://localhost:8081/api/v1/photos/show_entity/${photo.id}" class="picture" />
                           <p><a href="/album/${photo.album.id}">назад к альбому</a></p>`;
    if (isOwner) {
        htmlCode += `<button id="delete-button">удалить фотографию</button>`;
    }
    htmlCode += `<p>Фотография добавлена ${photo.creationTimeStamp}</p>`;

    photoDiv.insertAdjacentHTML('afterbegin', htmlCode);
}

function renderTags(data, isOwner) {

    let htmlCode = ``;

    if (isOwner) {
        htmlCode += `<p>Добавить тег</p>
                     <form id="create-tag">
                         <label for="tag">Введите название тега</label>
                         <input type="text" id="tag" name="tag" required>
                         <input type="hidden" name="photoId" value="${photoId}">
                         <button>добавить</button>
                     </form>`;
    }
    htmlCode += `<p id="tags-enum">Теги: `;
    let tags = data.tags;
    let tagsSize = tags.length;
    if (tagsSize === 0) {
        htmlCode += `<span id="no-tags">тегов еще не добавлено</span>`;
    } else {
        for (let i = 0; i < tagsSize; i++) {
            htmlCode += `<span>${tags[i].tag}`;
            if (isOwner) {
                htmlCode += `<button id="${tags[i].id}" class="delete-tag">x</button>`;
                htmlCode += `</span>`;
            } else
                if (i < tagsSize - 1) {
                    htmlCode += `, </span>`;
                } else htmlCode += `</span>`;
        }
    }

    htmlCode += `</p>`;

    tagsDiv.insertAdjacentHTML('afterbegin', htmlCode);
}

function renderRatings(data) {
    const rating = data.rating;

    let htmlCode = ``;

    if (rating !== null) {
        htmlCode += `<p class="rating" id="rating">Рейтинг: ${data.rating}%</p>`;
    } else {
        htmlCode += `<p class="rating" id="no-rating">Фотография еще не оценивалась</p>`;
    }
    htmlCode += `<p>Оценить фотографию `;

    let userRating = data.userRating;

    switch (userRating) {
        case true:
            htmlCode += `<button class="rating-buttons" id="true" style="background-color: green;">+</button>
                         <button class="rating-buttons" id="false">-</button>`;
            break;
        case false:
            htmlCode += `<button class="rating-buttons" id="true">+</button>
                         <button class="rating-buttons" id="false" style="background-color: red;">-</button>`;
            break;
        case null:
            htmlCode += `<button class="rating-buttons" id="true">+</button>
                         <button class="rating-buttons" id="false">-</button>`;
    }

    htmlCode += `</p>`;
    ratingsDiv.insertAdjacentHTML('afterbegin', htmlCode);
}

function renderComments(data) {
    const comments = data.comments;

    let htmlCode = `<p class="clear-fix">Добавить комментарий</p>`;

    htmlCode += `<form id="create-comment">
                     <label for="comment">Введите текст комментария</label><br>
                     <textarea id="comment" name="comment"></textarea><br>
                     <input type="hidden" name="photoId" value="${photoId}">
                     <button>добавить</button>
                 </form>
                 <p>Комментарии</p>`;

    commentsDiv.insertAdjacentHTML('beforebegin', htmlCode);

    htmlCode = ``;

    if (comments.length === 0) {
        htmlCode += '<p id="no-comments">Нет комментариев</p>';
    } else {
        let commentingUser;
        let comment;
        comments.forEach(commentDto => {
            comment = commentDto.comment;
            commentingUser = comment.commentingUser;
            htmlCode += `<div>
                             <p>
                                 <a href="/profile/${commentingUser.id}">
                                     ${commentingUser.firstName} ${commentingUser.lastName}
                                 </a>
                             </p>
                             <p>${comment.comment}</p>
                             <p>${comment.commentingTimeStamp}</p>`;
            if (commentDto.isCommentingUser) {
                htmlCode += `<button id="${comment.id}" class="delete-comment-button">удалить комментарий</button>`;
            }
            htmlCode += '</div>';
        });
    }

    commentsDiv.insertAdjacentHTML('afterbegin', htmlCode);
}

function renderError(data) {
    let htmlCode = `<p>${data.error}</p>`;
    photoDiv.insertAdjacentHTML('afterbegin', htmlCode);
}

show();