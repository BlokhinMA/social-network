body.addEventListener('click', (e) => {
    const button = e.target;

    if (button && button.id === 'delete-button') {

        fetch(`http://localhost:8081/api/v1/photos/delete/${photoId}`, {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(async response => {

                removeErrorElements();

                const data = await response.json();

                if (response.ok) {
                    window.location = `/album/${data.album.id}`;
                } else {
                    renderError(data, button);
                }

            });

    }
});