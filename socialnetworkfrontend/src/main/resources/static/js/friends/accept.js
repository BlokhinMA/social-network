function request(flag, method) {
    document.addEventListener('click', async (e) => {

        const button = e.target;

        if (!button?.classList.contains(`${flag}-friend-buttons`)) return;

        const response = await fetch(`http://localhost:8081/api/v1/friendships/${flag}/${button.id}`, {
            method: method,
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

    });
}

request('accept', 'PATCH');