document.querySelector('body').addEventListener('click', (e) => {
    const button = e.target;

    if (button && button.classList.contains('delete-comment-button')) {

        fetch(`/api/photos/delete_comment/${button.id}`, {
            method: 'DELETE'
        })
            .then(async response => {

                if (response.ok) {

                    removeErrorElements();

                    button.parentNode.remove();

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