const photoId = window.location.pathname.split('/').pop();
const tagsDiv = document.getElementById('tags');
const ratingDiv = document.getElementById('rating');
const commentsDiv = document.getElementById('comments');

let isOwner;

fetch(`/api/photos/show/${photoId}`, {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();

        let htmlCode = ``;

        const photoDiv = document.getElementById('photo');

        if (response.ok) {
            isOwner = data.owner;

            htmlCode += `<img alt="" src="/api/photos/show_entity/${data.photo.id}" />`;
            if (isOwner) {
                htmlCode += `<button id="delete-button">удалить</button>`;
            }
            htmlCode += `<p>${data.photo.creationTimeStamp}</p>`;

            const rating = data.rating;
            if (rating !== null) {
                htmlCode += `<p id="rating">Рейтинг: ${data.rating}%</p>`;
            } else {
                htmlCode += `<p id="no-rating">Фотография еще не оценивалась</p>`;
            }
            htmlCode += `<p>Оценить фотографию `;

            switch (data.userRating) {
                case true:
                    htmlCode += `<button style="background-color: green;">+</button>
                                 <button>-</button>`;
                    break;
                case false:
                    htmlCode += `<button>+</button>
                                 <button style="background-color: red;">-</button>`;
                    break;
                case null:
                    htmlCode += `<button>+</button>
                                 <button>-</button>`;
            }

            htmlCode += `</p>`;
            photoDiv.insertAdjacentHTML('afterbegin', htmlCode);

            htmlCode = ``;

            if (isOwner) {
                htmlCode += `<p>Добавить тег</p>
                            <form>
                                <label for="tag">Введите название тега</label>
                                <input type="text" id="tag" name="tag">
                                <input type="hidden" name="photoId" value="${photoId}">
                                <button>добавить</button>
                            </form>`;
            }
            htmlCode += `<p>Теги: `;
            data.tags.forEach(tag => {
                htmlCode += `<span>${tag.tag}`;
                if (isOwner) {
                    htmlCode += ` <button id="${tag.id}" class="delete-tag-button">x</button>`;
                }
                htmlCode += `,</span> `; // todo исправить последнюю запятую
            });
            htmlCode += `</p>`;

            tagsDiv.insertAdjacentHTML('afterbegin', htmlCode);

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