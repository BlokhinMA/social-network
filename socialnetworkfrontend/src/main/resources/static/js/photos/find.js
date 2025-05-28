const form = document.getElementById('find-photos');

form.addEventListener('submit', async (e) => {

    e.preventDefault();

    const formData = new FormData(form);
    const dataFromForm = Object.fromEntries(formData.entries());

    const response = await fetch('http://localhost:8081/api/v1/photos/find', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(dataFromForm)
    });

    document.querySelectorAll('.errors').forEach(el => el.remove());

    const data = await response.json();

    let htmlCode = ``;

    if (response.ok) {

        if (data.length === 0) {
            htmlCode = '<p>Ничего не найдено</p>';
        } else {
            data.forEach((photoId) => {
                htmlCode += `<a href="/photo/${photoId}" class="reset">
                                     <img alt="" src="http://localhost:8081/api/v1/photos/show_entity/${photoId}"
                                     class="pictures" />
                                 </a>`;
            });
        }

        document.getElementById('photos').innerHTML = htmlCode;

    } else {
        for (const [key, value] of Object.entries(data)) {
            htmlCode = `<div class="errors" style="color: red;">${value}</div>`;
            if (key === 'error') {
                document.getElementById('submit').insertAdjacentHTML('afterend', htmlCode);
            } else {
                document.querySelector(`[name="${key}"]`).insertAdjacentHTML('afterend', htmlCode);
            }
        }
    }

});