body.addEventListener('click', (e) => {
    const button = e.target;

    if (button && button.classList.contains('delete-tag')) {

        fetch(`http://localhost:8081/api/v1/photos/delete_tag/${button.id}`, {
            method: 'DELETE',
            credentials: 'include'
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