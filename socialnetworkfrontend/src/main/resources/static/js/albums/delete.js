albumsDiv.addEventListener('click', (e) => {
    if (e.target && e.target.classList.contains('delete-button')) {
        const button = e.target;
        const id = button.id;

        fetch(`/api/albums/delete/${id}`, {
            method: 'DELETE'
        })
            .then(async response => {

                if (response.ok) {
                    button.parentElement.remove();
                } else {
                    const data = await response.json();
                    let htmlCode = `<span style="color: red;">${data.error}</span>`;
                    button.insertAdjacentHTML('afterend', htmlCode);
                }

            })
    }
});