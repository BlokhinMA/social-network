document.addEventListener('click', async (e) => {
    const button = e.target;

    if (!button?.classList.contains('delete-comment-button')) return;

    const response = await fetch(`http://localhost:8081/api/v1/photos/delete_comment/${button.id}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    if (response.ok) {

        removeErrorElements();

        button.parentNode.remove();

        const commentsDiv = document.getElementById('comments');

        if (commentsDiv.childNodes.length === 0) {
            commentsDiv.innerHTML = '<p id="no-comments">Нет комментариев</p>';
        }

    } else {
        const data = await response.json();
        renderError(data, button);
    }

});

function renderError(data, button) {
    let htmlCode = `<span id="error" style="color: red;">${data.error}</span>`
    button.insertAdjacentHTML('afterend', htmlCode);
}