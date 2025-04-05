const communitiesDiv = document.getElementById('communities');

communitiesDiv.addEventListener('click', function (event) {
    if (event.target && event.target.classList.contains('deleteButton')) {
        button = event.target;
        const id = button.getAttribute('id');
        fetch(`/api/communities/delete/${id}`, {
            method: 'DELETE'
        })
            .then(async response => {
                errorElement = document.getElementById('error');
                if (errorElement) {
                    errorElement.remove();
                }
                if (!response.ok) {
                    const data = await response.json();
                    error = data.name;
                    htmlCode = `<span id="error">${error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                    throw new Error(`Ошибка ${response.status}: ${error}`);
                }
                return response.json();
            })
            .then(data => {
                const p = button.parentElement;
                p.remove();
                if (communitiesDiv.children.length === 0) {
                    htmlCode = `<p id="noOne">Вы пока не создали ни одно сообщество</p>`;
                    communitiesDiv.insertAdjacentHTML('beforeend', htmlCode);
                }
            });
    }
});