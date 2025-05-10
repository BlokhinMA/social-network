albumsDiv.addEventListener('click', async (e) => {
    const button = e.target;
    if (button && button.classList.contains('delete-album')) {

        const response = await fetch(`http://localhost:8081/api/v1/albums/delete/${button.id}`, {
            method: 'DELETE',
            credentials: 'include'
        });

        if (response.ok) {
            button.parentElement.remove();
            if (albumsDiv.childNodes.length === 0) {
                albumsDiv.innerHTML = '<p id="no-albums"></p>';
            }
        } else {
            const data = await response.json();
            let htmlCode = `<span style="color: red;">${data.error}</span>`;
            button.insertAdjacentHTML('afterend', htmlCode);
        }

    }
});