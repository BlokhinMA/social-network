const postsDiv = document.getElementById('posts');

postsDiv.addEventListener('click', function (event) {
    if (event.target && event.target.classList.contains('delete-post-button')) {
        const button = event.target;
        const id = button.getAttribute('id');
        fetch(`http://localhost:8081/api/v1/communities/delete_post/${id}`, {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(async response => {
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
                        htmlCode += `<p id="noOnePost">Нет постов</p>`;
                        postsDiv.insertAdjacentHTML('beforeend', htmlCode);
                    }
                } else {
                    const data = await response.json();
                    htmlCode += `<span id="error" style="color: red;">${data.error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                }
            });
    }
});