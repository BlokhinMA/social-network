const communitiesDiv = document.getElementById('communities');

communitiesDiv.addEventListener('click', function (event) {
    if (event.target && event.target.classList.contains('delete-community-buttons')) {
        const button = event.target;
        const id = button.getAttribute('id');
        fetch(`http://localhost:8081/api/v1/communities/delete/${id}`, {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(async response => {
                let errorElement = document.getElementById('error');
                if (errorElement) {
                    errorElement.remove();
                }
                let htmlCode;
                if (response.ok) {
                    const p = button.parentElement;
                    p.remove();
                    if (communitiesDiv.children.length === 0) {
                        htmlCode = `<p id="no-communities">Вы пока не создали ни одно сообщество</p>`;
                        communitiesDiv.insertAdjacentHTML('beforeend', htmlCode);
                    }
                } else {
                    const data = await response.json();
                    htmlCode = `<span id="error" style="color: red;">${data.error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                }
            });
    }
});