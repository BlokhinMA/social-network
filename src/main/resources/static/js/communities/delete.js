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
                const data = await response.json();
                if (response.ok) {
                    const p = button.parentElement;
                    p.remove();
                    if (communitiesDiv.children.length === 0) {
                        htmlCode = `<p id="noOne">Вы пока не создали ни одно сообщество</p>`;
                        communitiesDiv.insertAdjacentHTML('beforeend', htmlCode);
                    }
                } else {
                    error = data.name;
                    htmlCode = `<span id="error">${error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                }
            });
    }
});