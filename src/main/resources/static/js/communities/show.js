const communityId = window.location.pathname.split('/').pop();

let isCreator;
let isMember;

fetch(`/api/communities/show/${communityId}`, {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();
        if (response.ok) {

            const communityFooter = document.getElementById('community-footer');
            let htmlCode = `<h1 id="community-name" class="community-header">${data.community.name}</h1>`;
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
            communityFooter.insertAdjacentHTML('beforeend', htmlCode);

            const membersDiv = document.getElementById('members');
            if (Object.keys(data.members).length === 0) {
                htmlCode = '<p id="noOneMember">Нет подписчиков</p>';
            } else {
                htmlCode = '<p>Подписчики</p>';
                data.members.forEach(member => {
                    htmlCode += `<p id="${member.id}"><a href="/profile/${member.member.id}">${member.member.firstName} ${member.member.lastName}</a>`;
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
            if (Object.keys(data.posts).length === 0) {
                htmlCode += '<p id="noOnePost">Нет постов</p>';
            } else {
                htmlCode += '<p>Посты</p>';
                data.posts.forEach(post => {
                    htmlCode += `<div id="${post.id}">
                                     <p><a href="/profile/${post.author.id}">${post.author.firstName} ${post.author.lastName}</a></p>
                                     <p>${post.postText}</p>
                                     <p>${post.creationTimeStamp}</p>`;
                    if (post.isAuthor) {
                        htmlCode += `<button id="${post.id}" class="delete-post-button">удалить пост</button>`;
                    }
                    htmlCode += '</div>';
                });
            }
            postsDiv.insertAdjacentHTML('afterbegin', htmlCode);
        }
    });