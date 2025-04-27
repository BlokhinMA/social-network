body.addEventListener('click', (e) => {
    const button = e.target;

    if (button && button.classList.contains('delete-tag-button')) {

        fetch(`/api/photos/delete_tag/${button.id}`, {
            method: 'DELETE'
        })
            .then(response => {

                removeErrorElements();

                if (response.ok) {

                    button.parentElement.remove();

                } else {
                    const data = response.json();
                    renderError(data, button);
                }
            });

    }

});