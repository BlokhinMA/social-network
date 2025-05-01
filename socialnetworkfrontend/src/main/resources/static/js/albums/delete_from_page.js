albumHeader.addEventListener('click', (e) => {
    if (e.target && e.target.id === 'delete-album-button') {
        const button = e.target;

        fetch(`http://localhost:8081/api/v1/albums/delete/${albumId}`, {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(async response => {

                if (response.ok) {
                    window.location = '/my_albums';
                } else {
                    const data = await response.json();
                    let htmlCode = `<span>${data.error}</span>`;
                    button.insertAdjacentHTML('afterend', htmlCode);
                }
            });
    }

});