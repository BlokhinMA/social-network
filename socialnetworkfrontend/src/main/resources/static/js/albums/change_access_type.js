const body = document.querySelector('body');

body.addEventListener('submit', async (e) => {

    // const form = e.target;

    e.preventDefault();

    if (e.target && e.target.id === 'change-access-type') {

        // e.preventDefault();

        console.log('change-access-type');

        // const formData = new FormData(form);
        // const dataFromForm = Object.fromEntries(formData.entries());
        //
        // const response = await fetch('http://localhost:8081/api/v1/albums/change_access_type', {
        //     method: 'PATCH',
        //     credentials: 'include',
        //     headers: {
        //         'Content-type': 'application/json',
        //     },
        //     body: JSON.stringify(dataFromForm)
        // });
        //
        // const error = document.getElementById('error');
        // if (error) {
        //     error.remove();
        // }
        //
        // const data = await response.json();
        //
        // if (response.ok) {
        //
        //     let accessType = data.accessType;
        //     const accessTypeP = body.querySelector('#access-type');
        //     if (accessType)
        //         accessTypeP.innerText = `Тип доступа: ${accessType === 'ALL' ? 'для всех' : 'для друзей'}`;
        //
        // } else {
        //     let htmlCode = `<div id="error">`;
        //     for (const [key, value] of Object.entries(data)) {
        //         htmlCode += `<p style="color: red;">${value}</p>`;
        //     }
        //     htmlCode += `</div>`;
        //     form.insertAdjacentHTML('afterend', htmlCode);
        // }

    }

});