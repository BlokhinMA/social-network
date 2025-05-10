body.addEventListener('click', async (e) => {
    const button = e.target;

    if (button && button.classList.contains('delete-tag')) {

        const response = await fetch(`http://localhost:8081/api/v1/photos/delete_tag/${button.id}`, {
            method: 'DELETE',
            credentials: 'include'
        });

        removeErrorElements();

        if (response.ok) {

            button.parentElement.remove();

        } else {
            const data = response.json();
            renderError(data, button);
        }

    }

});