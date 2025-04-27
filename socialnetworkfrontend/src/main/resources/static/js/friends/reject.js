document.querySelector('body').addEventListener('click', (e) => {

    const button = e.target;

    if (button && button.classList.contains('reject-friend')) {

        fetch(`/api/friendships/reject/${button.id}`, {
            method: 'DELETE'
        })
            .then(async response => {

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

            })

    }

});