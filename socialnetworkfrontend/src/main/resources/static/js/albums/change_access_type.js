document.addEventListener('submit', async (e) => {

    e.preventDefault();

    const form = e.target;

    if (form?.getAttribute('id') !== 'change-access-type') return;

    const formData = new FormData(form);
    const dataFromForm = Object.fromEntries(formData.entries());

    const response = await fetch('http://localhost:8081/api/v1/albums/change_access_type', {
        method: 'PATCH',
        credentials: 'include',
        headers: {
            'Content-type': 'application/json',
        },
        body: JSON.stringify(dataFromForm)
    });

    const error = document.getElementById('error');
    if (error) {
        error.remove();
    }

    const data = await response.json();

    if (response.ok) {

        const accessType = data.accessType;
        const accessTypeP = document.getElementById('access-type');
        accessTypeP.innerText = `Тип доступа: ${accessType === 'ALL'
            ? 'для всех'
            : 'для друзей'}`;

        form.reset();

    } else {
        let htmlCode = `<div id="error">`;
        for (const value of Object.values(data)) {
            htmlCode += `<p style="color: red;">${value}</p>`;
        }
        htmlCode += `</div>`;
        form.insertAdjacentHTML('afterend', htmlCode);
    }

});