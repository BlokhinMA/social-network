document.querySelector('body').addEventListener('submit', (e) => {

    const form = e.target;

    if (form && form.id === 'create-photos') {

        e.preventDefault();

        const formData = new FormData(form);

        fetch('http://localhost:8081/api/v1/photos/create', {
            method: 'POST',
            credentials: 'include',
            body: formData
        })
            .then(async response => {

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
                                         <img alt="" src="http://localhost:8081/api/v1/photos/show_entity/${photo.id}" class="pictures" />
                                     </a>`;
                    });

                    document.querySelector('body').querySelector('#photos')
                        .insertAdjacentHTML('beforeend', htmlCode);


                } else {
                    renderErrors(data, form);
                }

            });

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
    for (const [key, value] of Object.entries(data)) {
        htmlCode += `<p style="color: red;">${value}</p>`;
    }
    htmlCode += '</div>';
    form.insertAdjacentHTML("afterend", htmlCode);
}