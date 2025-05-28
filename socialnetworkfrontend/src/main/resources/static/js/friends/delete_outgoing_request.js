document.addEventListener('click', async (e) => {

    const button = e.target;

    if (!button?.classList.contains('delete-outgoing-request')) return;

    const response = await fetch(`http://localhost:8081/api/v1/friendships/delete_outgoing_request/
        ${button.id}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    let errors = document.getElementById('errors');
    if (errors) {
        errors.remove();
    }

    if (response.ok) {

        button.parentElement.remove();

        const incomingRequests = document.getElementById('outgoing-requests');

        if (incomingRequests.children.length === 0) {
            incomingRequests.innerHTML = '<p>Вы пока не отправили никому заявку</p>';
        }

    } else {
        const data = await response.json();
        let htmlCode = `<span style="color: red;">${data.error}</span>`;
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});