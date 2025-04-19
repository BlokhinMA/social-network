const communitiesDiv = document.getElementById('communities');

communitiesDiv.addEventListener('click', function (event) {
    if (event.target && event.target.classList.contains('deleteButton')) {  // todo: исправить названия id
        const button = event.target;
        const id = button.getAttribute('id');
        fetch(`/api/communities/delete/${id}`, {
            method: 'DELETE'
        })
            .then(async response => {
                let errorElement = document.getElementById('error');
                if (errorElement) {
                    errorElement.remove();
                }
                const data = await response.json();
                let htmlCode;
                if (response.ok) {
                    const p = button.parentElement;
                    p.remove();
                    if (communitiesDiv.children.length === 0) {
                        htmlCode = `<p id="noOne">Вы пока не создали ни одно сообщество</p>`;
                        communitiesDiv.insertAdjacentHTML('beforeend', htmlCode);
                    }
                } else {
                    htmlCode = `<span id="error" style="color: red;">${data.error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                }
            });
    }
});