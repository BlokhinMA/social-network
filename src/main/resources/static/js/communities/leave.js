communityHeader.addEventListener("click", (e) => {
    if (e.target && e.target.id === "leave-button") {
        const button = e.target;

        fetch(`/api/communities/leave/${communityId}`, {
            method: "DELETE"
        })
            .then(async response => {
                const data = await response.json();

                let errorElement = document.getElementById('error');
                if (errorElement) {
                    errorElement.remove();
                }

                let htmlCode = '';
                if (response.ok) {

                    button.textContent = "подписаться";
                    button.id = "subscribe-button";

                    const membersDiv = document.getElementById('members');
                    const memberP = membersDiv.querySelector(`[id="${data.id}"]`);
                    memberP.remove();

                    if (membersDiv.children.length === 1) {
                        membersDiv.children.item(0).remove();
                        htmlCode += `<p id="noOneMember">Нет подписчиков</p>`;
                        membersDiv.insertAdjacentHTML('beforeend', htmlCode);
                    }

                    if (!isCreator) {
                        const body = document.querySelector('body');
                        const createPostForm = body.querySelector('#createPost');
                        createPostForm.remove();
                    }

                } else {
                    htmlCode += `<span id="error" style="color: red;">${data.error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                }

            });
    }
});