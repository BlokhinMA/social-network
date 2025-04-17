communityHeader.addEventListener("click", (e) => {
    if (e.target && e.target.id === "subscribe-button") {
        const button = e.target;

        fetch(`/api/communities/join/${communityId}`, {
            method: "GET",
        })
            .then(async response => {
                const data = await response.json();

                let errorElement = document.getElementById('error');
                if (errorElement) {
                    errorElement.remove();
                }

                let htmlCode = '';
                if (response.ok) {

                    button.textContent = "выйти из сообщества";
                    button.id = "leave-button";

                    const noOneP = document.getElementById('noOneMember');
                    const membersDiv = document.getElementById('members');

                    if (noOneP) {
                        noOneP.remove();
                        htmlCode += '<p>Подписчики</p>'
                        membersDiv.insertAdjacentHTML('afterbegin', htmlCode);
                    }

                    htmlCode = `<p id="${data.id}"><a href="/profile/${data.member.id}">${data.member.firstName} ${data.member.lastName}</a>`;
                    const firstChildMembersDiv = membersDiv.childNodes.item(0);
                    firstChildMembersDiv.insertAdjacentHTML('afterend', htmlCode);

                    const createPostForm = document.getElementById('createPost');
                    if (!createPostForm) {
                        htmlCode = `<form id="createPost">
                                    <label for="post-text">Добавить пост</label><br>
                                    <textarea id="post-text" name="postText" required></textarea>
                                    <input type="hidden" name="communityId" value="${communityId}">
                                    <button>добавить пост</button>
                                </form>`;
                        membersDiv.insertAdjacentHTML('afterend', htmlCode);
                    }
                } else {
                    htmlCode += `<span id="error" style="color: red;">${data.error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                }
            });

    }
});