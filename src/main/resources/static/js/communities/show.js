const communityId = window.location.pathname.split('/').pop();

const communityHeader = document.getElementById("community-header");

let isCreator;
let isMember;

fetch(`/api/communities/show/${communityId}`, {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();
        if (response.ok) {

            let htmlCode = `<h1 class="header">${data.community.name}</h1>`;
            isCreator = data.creator;
            isMember = data.member;
            if (isCreator) {
                htmlCode += `<button>удалить сообщество</button>`;
            }
            if (isMember) {
                htmlCode += '<button id="leave-button">выйти из сообщества</button>';
            } else {
                htmlCode += '<button id="subscribe-button">подписаться</button>';
            }
            communityHeader.insertAdjacentHTML('beforeend', htmlCode);

            const membersDiv = document.getElementById('members');
            if (Object.keys(data.members).length === 0) {
                htmlCode = '<p id="noOneMember">Нет подписчиков</p>';
            } else {
                htmlCode = '<p>Подписчики</p>';
                data.members.forEach(member => {
                    htmlCode += `<p><a href="/profile/${member.member.id}">
                                                          ${member.member.firstName} ${member.member.lastName}
                                                      </a>`;
                    if (isCreator && data.community.creator.id !== member.member.id) {
                        htmlCode += `<button id="${member.id}" class="kick-button">выгнать</button>`;
                    }
                    htmlCode += '</p>';
                });
            }
            membersDiv.insertAdjacentHTML('beforeend', htmlCode);

            const postsDiv = document.getElementById('posts');
            if (isCreator || isMember) {
                htmlCode = `<form id="createPost">
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
                htmlCode += '<p id="noOnePost">Нет постов</p>';  // todo: исправить названия id
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
            console.log(data.error);
        }
    });