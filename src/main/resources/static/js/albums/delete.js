albumsDiv.addEventListener('click', (e) => {
    if (e.target && e.target.classList.contains('delete-button')) {
        const button = e.target;
        const id = button.id;

        fetch(`/api/albums/delete/${id}`, {
            method: 'DELETE'
        })
            .then(async response => {
                const data = await response.json();

                if (response.ok) {
                    button.parentElement.remove();
                } else {
                    let htmlCode = `<span style="color: red;">${data.error}</span>`;
                    button.insertAdjacentHTML('afterend', htmlCode);
                }

            })
    }
});