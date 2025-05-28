document.addEventListener("click", async (e) => {

    const button = e.target;

    if (button?.id !== "leave-button") return;

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
        document.getElementById(`member${data.id}`).remove();

        if (membersDiv.children.length === 0) {
            htmlCode += `<p id="no-members">Нет подписчиков</p>`;
            membersDiv.insertAdjacentHTML('beforeend', htmlCode);
        }

        if (!isCreator) {
            document.getElementById('create-post').remove();
        }

    } else {
        htmlCode += `<span id="error" style="color: red;">${data.error}</span>`;
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});