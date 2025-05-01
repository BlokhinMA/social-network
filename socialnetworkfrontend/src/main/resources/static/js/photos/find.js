if (localStorage.getItem('userId') === null) {
    window.location = '/sign_in';
}

const body = document.querySelector('body');

body.addEventListener('submit', (e) => {

    const form = e.target;

    if (form && form.id === 'find-photos') {

        e.preventDefault();

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        fetch('http://localhost:8081/api/v1/photos/find', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
            .then(async response => {

                const errorsDivs = body.querySelector('.errors');
                if (errorsDivs) {
                    errorsDivs.remove();
                }

                const data = await response.json();

                let htmlCode = ``;

                if (response.ok) {

                    if (data.length === 0) {
                        htmlCode = '<p>Ничего не найдено</p>';
                    } else {
                        data.forEach((photoId) => {
                            htmlCode += `<a href="/photo/${photoId}" class="reset">
                                             <img alt="" src="http://localhost:8081/api/v1/photos/show_entity/${photoId}" class="pictures" />
                                         </a>`;
                        });
                    }

                    document.getElementById('photos').innerHTML = htmlCode;

                } else {
                    let htmlCode = ``;
                    for (const [key, value] of Object.entries(data)) {
                        htmlCode = `<div class="errors" style="color: red;">${value}</div>`;
                        if (key === 'error') {
                            body.querySelector('#submit').insertAdjacentHTML('afterend', htmlCode);
                        } else {
                            console.log(key, value);
                            body.querySelector(`[name="${key}"]`).insertAdjacentHTML('afterend', htmlCode);
                        }
                    }
                }

            });

    }

});