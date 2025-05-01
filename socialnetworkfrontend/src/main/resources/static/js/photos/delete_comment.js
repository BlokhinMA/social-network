body.addEventListener('click', (e) => {
    const button = e.target;

    if (button && button.classList.contains('delete-comment-button')) {

        fetch(`http://localhost:8081/api/v1/photos/delete_comment/${button.id}`, {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(async response => {

                if (response.ok) {

                    removeErrorElements();

                    button.parentNode.remove();

                    const commentsDiv = document.querySelector('body').querySelector('#comments');

                    if (commentsDiv.childNodes.length === 0) {
                        commentsDiv.innerHTML = '<p id="noOneComment">Нет комментариев</p>';
                    }

                } else {
                    const data = await response.json();
                    renderError(data, button);
                }

            });

    }

});

function renderError(data, button) {
    let htmlCode = `<span id="error" style="color: red;">${data.error}</span>`
    button.insertAdjacentHTML('afterend', htmlCode);
}