document.addEventListener('click', async (e) => {
    const button = e.target;

    if (button?.id !== 'delete-button') return;

    const response = await fetch(`http://localhost:8081/api/v1/photos/delete/${photoId}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    removeErrorElements();

    const data = await response.json();

    if (response.ok) {
        window.location = `/album/${data.album.id}`;
    } else {
        renderError(data, button);
    }

});