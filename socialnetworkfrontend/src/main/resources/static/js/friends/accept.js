document.querySelector('body').addEventListener('click', async (e) => {

    const button = e.target;

    if (button && button.classList.contains('accept-friend-buttons')) {

        const response = await fetch(`http://localhost:8081/api/v1/friendships/accept/${button.id}`, {
            method: 'PATCH',
            credentials: 'include'
        });

        let errors = document.getElementById('errors');
        if (errors) {
            errors.remove();
        }

        if (response.ok) {

            button.parentElement.remove();

            const incomingRequests = document.getElementById('incoming-requests');

            if (incomingRequests.children.length === 0) {
                incomingRequests.innerHTML = '<p>Входящих заявок пока нет</p>';
            }

        } else {
            const data = await response.json();
            let htmlCode = `<span style="color: red;">${data.error}</span>`;
            button.insertAdjacentHTML('afterend', htmlCode);
        }

    }

});