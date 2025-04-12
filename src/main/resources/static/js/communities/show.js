const communityId = window.location.pathname.split('/').pop();

async function getIsMemberAndIsCreator() {
    let response = await fetch(`/api/communities/is_member/${communityId}`, {method: 'GET'});
    let data = await response.json();
    let isMember = null;
    if (response.ok) {
        isMember = data.is;
    } else {
        console.log(data.error);
    }

    response = await fetch(`/api/communities/is_creator/${communityId}`, {method: 'GET'});
    data = await response.json();
    let isCreator = null;
    if (response.ok) {
        isCreator = data.is;
    } else {
        console.log(data.error);
    }

    show(isMember, isCreator);

}

function show(isMember, isCreator) {

    fetch(`/api/communities/show/${communityId}`, {
        method: 'GET'
    })
        .then(async response => {
            const data = await response.json();
            if (response.ok) {
                const communityName = data.name;
                document.querySelector('title').innerText = communityName;
                const communityNameElement = document.getElementById('community-name');
                communityNameElement.innerText = communityName;
                if (!isMember) {
                    const subscribeButtonHtml = '<button id="subscribe">подписаться</button>';
                    communityNameElement.insertAdjacentHTML('afterend', subscribeButtonHtml);
                } else {

                }
            } else {
                console.log(data.error);
            }
        });

    fetch(`/api/communities/show_all_members/${communityId}`, {
        method: 'GET'
    })
        .then(async response => {
            const data = await response.json();
            let htmlCode;
            if (response.ok) {
                const membersDiv = document.getElementById('members');
                if (Object.keys(data).length === 0) {
                    htmlCode = '<p>Нет подписчиков</p>';
                } else {
                    htmlCode = '<p>Подписчики</p>';
                    data.forEach(member => {
                        htmlCode += `<p><a href="/profile/${member.member.id}">${member.member.firstName} ${member.member.lastName}</a>`;
                        if (isCreator) {
                            htmlCode += `<button id="${member.id}">выгнать</button>`;
                        }
                        htmlCode += '</p>';
                    });
                }
                membersDiv.insertAdjacentHTML('beforeend', htmlCode);
            }
        });

    fetch(`/api/communities/show_all_posts/${communityId}`, {
        method: 'GET'
    })
        .then(async response => {
            const postsDiv = document.getElementById('posts');
            let htmlCode;
            if (isCreator || isMember) {
                htmlCode = `<form id="createPost">
                            <label for="post-text">Добавить пост</label><br>
                            <textarea id="post-text" name="postText" required></textarea>
                            <input type="hidden" name="communityId" th:value="${communityId}">
                            <button>добавить пост</button>
                        </form>`;
                postsDiv.insertAdjacentHTML('beforeend', htmlCode);
            }
            let data = await response.json();
            if (response.ok) {
                htmlCode = '';
                if (Object.keys(data).length === 0) {
                    htmlCode += '<p>Нет постов</p>';
                } else {
                    data.forEach(post => {
                        htmlCode += `<div id="${post.id}">
                                <p><a href="/profile/${post.author.id}">${post.author.firstName} ${post.author.lastName}</a></p>
                                <p>${post.postText}</p>
                                <p>${post.creationTimeStamp}</p>
                                </div>`;
                    });
                }
                postsDiv.insertAdjacentHTML('beforeend', htmlCode);
            } else {
                console.log(data.error);
            }
        });
}

getIsMemberAndIsCreator();