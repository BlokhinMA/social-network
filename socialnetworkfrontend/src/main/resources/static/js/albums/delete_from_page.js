document.addEventListener('click', async (e) => {

    const button = e.target;

    if (button?.id !== 'delete-album-button') return;

    const response = await fetch(`http://localhost:8081/api/v1/albums/delete/${albumId}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    if (response.ok) {
        window.location = '/my_albums';
    } else {
        const data = await response.json();
        let htmlCode = `<span>${data.error}</span>`;
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});