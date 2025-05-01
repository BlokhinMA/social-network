albumsDiv.addEventListener('click', (e) => {
    if (e.target && e.target.classList.contains('delete-album')) {
        const button = e.target;
        const id = button.id;

        fetch(`http://localhost:8081/api/v1/albums/delete/${id}`, {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(async response => {

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

            })
    }
});