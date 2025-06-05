document.addEventListener('submit', async (e) => {

    e.preventDefault();

    const form = e.target;

    if (form?.id !== 'create-photos') return;

    const formData = new FormData(form);

    const response = await fetch('http://localhost:8081/api/v1/photos/create', {
        method: 'POST',
        credentials: 'include',
        body: formData
    });

    const data = await response.json();

    removeErrorElements();

    if (response.ok) {

        const noPhotosP = document.getElementById("no-photos");

        if (noPhotosP) {
            noPhotosP.remove();
        }

        let htmlCode = '';

        data.forEach((photo) => {
            htmlCode += `<a href="/photo/${photo.id}" class="reset">
                                 <img alt="" src="http://localhost:8081/api/v1/photos/show_entity/${photo.id}"
                                 class="pictures" />
                             </a>`;
        });

        document.getElementById('photos').insertAdjacentHTML('beforeend', htmlCode);

        form.reset();

    } else {
        renderErrors(data, form);
    }

});

function removeErrorElements() {
    let errorElement = document.getElementById("error");
    if (errorElement) {
        errorElement.remove();
    }
}

function renderErrors(data, form) {
    let htmlCode = '<div id="error">';
    for (const value of Object.values(data)) {
        htmlCode += `<p style="color: red;">${value}</p>`;
    }
    htmlCode += '</div>';
    form.insertAdjacentHTML("afterend", htmlCode);
}