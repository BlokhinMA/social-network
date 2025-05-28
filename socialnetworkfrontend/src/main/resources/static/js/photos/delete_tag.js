document.addEventListener('click', async (e) => {
    const button = e.target;

    if (!button?.classList.contains('delete-tag')) return;

    const response = await fetch(`http://localhost:8081/api/v1/photos/delete_tag/${button.id}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    removeErrorElements();

    if (response.ok) {

        button.parentElement.remove();

        const tagsEnumP = document.getElementById('tags-enum');

        if (tagsEnumP.children.length === 0) {
            let htmlCode = `<span id="no-tags">тегов еще не добавлено</span>`;
            tagsEnumP.insertAdjacentHTML('beforeend', htmlCode);
        }

    } else {
        const data = response.json();
        renderError(data, button);
    }

});