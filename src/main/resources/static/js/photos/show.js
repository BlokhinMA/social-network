const photoId = window.location.pathname.split('/').pop();

const photoDiv = document.getElementById('photo');
const tagsDiv = document.getElementById('tags');
const ratingsDiv = document.getElementById('ratings');
const commentsDiv = document.getElementById('comments');

let isOwner;
// let userRating;

fetch(`/api/photos/show/${photoId}`, {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();

        let htmlCode = ``;

        if (response.ok) {
            isOwner = data.owner;

            htmlCode += `<img alt="" src="/api/photos/show_entity/${data.photo.id}" />`;
            if (isOwner) {
                htmlCode += `<button id="delete-button">удалить</button>`;
            }
            htmlCode += `<p>${data.photo.creationTimeStamp}</p>`;

            photoDiv.insertAdjacentHTML('afterbegin', htmlCode);

            htmlCode = ``;

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
            if (data.tags.size === 0) {
                htmlCode += `<span id="no-tags">тегов еще не добавлено</span>`;
            } else {
                data.tags.forEach(tag => {
                    htmlCode += `<span>${tag.tag}`;
                    if (isOwner) {
                        htmlCode += ` <button id="${tag.id}" class="delete-tag-button">x</button>`;
                    }
                    htmlCode += `,</span> `; // todo: исправить последнюю запятую
                });
            }

            htmlCode += `</p>`;

            tagsDiv.insertAdjacentHTML('afterbegin', htmlCode);

            htmlCode = ``;

            const rating = data.rating;
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

            htmlCode = ``;

            let commentingUser;
            let comment;

            const comments = data.comments;

            if (comments.length === 0) {
                htmlCode += '<p id="noOneComment">Нет комментариев</p>';
            } else {
                comments.forEach(commentDto => {
                    comment = commentDto.comment;
                    commentingUser = comment.commentingUser;
                    htmlCode += `<div>
                                 <p><a href="/profile/${commentingUser.id}">
                                     ${commentingUser.firstName} ${commentingUser.lastName}
                                 </a></p>
                                 <p>${comment.comment}</p>
                                 <p>${comment.commentingTimeStamp}</p>`;
                    if (commentDto.commentingUser) {
                        htmlCode += `<button id="${comment.id}" class="delete-post-button">удалить комментарий</button>`;
                    }
                    htmlCode += '</div>';
                });
            }

            commentsDiv.insertAdjacentHTML('afterbegin', htmlCode);

        } else {
            htmlCode = `<p>${data.error}</p>`;
            photoDiv.insertAdjacentHTML('afterbegin', htmlCode);
        }
    });