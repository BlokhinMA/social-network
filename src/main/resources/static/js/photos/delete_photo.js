document.querySelector('body').addEventListener('click', (e) => {
    const button = e.target;

    if (button && button.id === 'delete-button') {

        fetch(`/api/photos/delete/${photoId}`, {
            method: 'DELETE'
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