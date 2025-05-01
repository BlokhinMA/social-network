const body = document.querySelector('body');

body.addEventListener('submit', (e) => {

    const form = e.target;

    if (form && form.id === 'change-access-type') {

        e.preventDefault();

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        fetch('http://localhost:8081/api/v1/albums/change_access_type', {
            method: 'PATCH',
            credentials: 'include',
            headers: {
                'Content-type': 'application/json',
            },
            body: JSON.stringify(data)
        })
            .then(async response => {

                const error = document.getElementById('error');
                if (error) {
                    error.remove();
                }

                const data = await response.json();

                if (response.ok) {

                    let accessType = data.accessType;
                    const accessTypeP = body.querySelector('#access-type');
                    if (accessType === 'ALL') {
                        accessTypeP.innerText = `Тип доступа: для всех`;
                    } else {
                        accessTypeP.innerText = `Тип доступа: для друзей`;
                    }

                } else {
                    let htmlCode = `<div id="error">`;
                    for (const [key, value] of Object.entries(data)) {
                        htmlCode += `<p style="color: red;">${value}</p>`;
                    }
                    htmlCode += `</div>`;
                    form.insertAdjacentHTML('afterend', htmlCode);
                }

            });

    }

});