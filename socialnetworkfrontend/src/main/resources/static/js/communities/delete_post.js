const postsDiv = document.getElementById('posts');

document.addEventListener('click', async (e) => {

    const button = e.target;

    if (!button?.classList.contains('delete-post-button')) return;

    const response = await fetch(`http://localhost:8081/api/v1/communities/delete_post/${button.id}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    let errorElement = document.getElementById('error');
    if (errorElement) {
        errorElement.remove();
    }
    let htmlCode = '';
    if (response.ok) {
        const div = button.parentElement;
        div.remove();
        if (postsDiv.children.length === 1) {
            postsDiv.children.item(0).remove();
            htmlCode += `<p id="no-posts">Нет постов</p>`;
            postsDiv.insertAdjacentHTML('beforeend', htmlCode);
        }
    } else {
        const data = await response.json();
        htmlCode += `<span id="error" style="color: red;">${data.error}</span>`
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});