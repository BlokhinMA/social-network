communityHeader.addEventListener("click", async (e) => {

    const button = e.target;

    if (button && button.id === "leave-button") {

        const response = await fetch(`http://localhost:8081/api/v1/communities/leave/${communityId}`, {
            method: 'DELETE',
            credentials: 'include'
        });

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
                htmlCode += `<p id="no-members">Нет подписчиков</p>`;
                membersDiv.insertAdjacentHTML('beforeend', htmlCode);
            }

            if (!isCreator) {
                const body = document.querySelector('body');
                const createPostForm = body.querySelector('#create-post');
                createPostForm.remove();
            }

        } else {
            htmlCode += `<span id="error" style="color: red;">${data.error}</span>`;
            button.insertAdjacentHTML('afterend', htmlCode);
        }

    }
});