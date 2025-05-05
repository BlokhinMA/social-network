const communityId = window.location.pathname.split('/').pop();

const communityHeader = document.getElementById("community-header");

let isCreator;
let isMember;

fetch(`http://localhost:8081/api/v1/communities/show/${communityId}`, {
    method: 'GET',
    credentials: 'include'
})
    .then(async response => {
        const data = await response.json();
        let htmlCode = ``;
        if (response.ok) {

            let htmlCode = `<h1 class="header">${data.community.name}</h1>`;
            isCreator = data.creator;
            isMember = data.member;
            if (isCreator) {
                htmlCode += `<button id="delete-community-button">удалить сообщество</button>`;
            }
            if (isMember) {
                htmlCode += '<button id="leave-button">выйти из сообщества</button>';
            } else {
                htmlCode += '<button id="subscribe-button">подписаться</button>';
            }
            htmlCode += '<p>Подписчики</p>';
            communityHeader.insertAdjacentHTML('beforeend', htmlCode);

            if (Object.keys(data.members).length === 0) {
                htmlCode = '<p id="no-members">Нет подписчиков</p>';
            } else {
                data.members.forEach(member => {
                    htmlCode += `<p><a href="/profile/${member.member.id}">
                                                          ${member.member.firstName} ${member.member.lastName}
                                                      </a>`;
                    if (isCreator && data.community.creator.id !== member.member.id) {
                        htmlCode += `<button id="${member.id}" class="kick-buttons">выгнать</button>`;
                    }
                    htmlCode += '</p>';
                });
            }
            document.getElementById('members').insertAdjacentHTML('beforeend', htmlCode);

            const postsDiv = document.getElementById('posts');
            if (isCreator || isMember) {
                htmlCode = `<form id="create-post">
                                <label for="post-text">Добавить пост</label><br>
                                <textarea id="post-text" name="postText" required></textarea>
                                <input type="hidden" name="communityId" value="${communityId}">
                                <button>добавить пост</button>
                            </form>`;
                postsDiv.insertAdjacentHTML('beforebegin', htmlCode);
            }
            htmlCode = '';
            const posts = data.posts;
            if (posts.length === 0) {
                htmlCode += '<p id="no-posts">Нет постов</p>';
            } else {
                htmlCode += '<p>Посты</p>';
                posts.forEach(post => {
                    htmlCode += `<div>
                                     <p><a href="/profile/${post.author.id}">
                                             ${post.author.firstName} ${post.author.lastName}
                                         </a></p>
                                     <p>${post.postText}</p>
                                     <p>${post.creationTimeStamp}</p>`;
                    if (post.isAuthor) {
                        htmlCode += `<button id="${post.id}" class="delete-post-button">удалить пост</button>`;
                    }
                    htmlCode += '</div>';
                });
            }
            postsDiv.insertAdjacentHTML('afterbegin', htmlCode);
        } else {
            htmlCode += `<p>${data.error}</p>`;
            communityHeader.insertAdjacentHTML('beforeend', htmlCode);
        }
    });