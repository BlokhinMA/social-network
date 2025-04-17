const albumsDiv = document.getElementById("albums");
const form = document.getElementById('create-album');

form.addEventListener('submit', (e) => {
    e.preventDefault();

    const formData = new FormData(form);

    fetch(`/api/albums/create`, {
        method: 'POST',
        body: formData
    })
        .then(async response => {
            const errorElements = document.querySelectorAll('.error');
            if (errorElements.length) {
                errorElements.forEach((element) => {
                    element.remove();
                });
            }
            const data = await response.json();
            let htmlCode;
            if (response.ok) {
                const noOneP = document.getElementById("noOne");
                if (noOneP) {
                    noOneP.remove();
                }
                htmlCode = `<p>
                                <a href="/album/${data.id}">${data.title}</a>
                                <button class="delete-button" id="${data.id}">удалить</button>
                            </p>`;
                albumsDiv.insertAdjacentHTML("beforeend", htmlCode);
                form.reset();
            } else {
                let div;
                for (const [key, value] of Object.entries(data)) {
                    div = document.createElement('div');
                    div.classList.add('error');
                    div.style.color = 'red';
                    div.textContent = value;
                    if (key === 'error') {
                        form.insertAdjacentElement('beforeend', div);
                    } else {
                        if (key === `accessType`) {
                            document.getElementById(`radio-buttons`).insertAdjacentElement('beforeend', div);
                        } else {
                            document.querySelector(`[name="${key}"]`).insertAdjacentElement("afterend", div);
                        }
                    }
                }
            }
        });
});