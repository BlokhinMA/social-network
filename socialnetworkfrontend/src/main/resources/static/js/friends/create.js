document.addEventListener('click', async (e) => {

    const button = e.target;

    if (!button?.classList.contains('make-friend')) return;

    const response = await fetch(`http://localhost:8081/api/v1/friendships/create/${button.id}`, {
        method: 'GET',
        credentials: 'include'
    });

    let errors = document.getElementById('error');
    if (errors) {
        errors.remove();
    }

    const data = await response.json();

    let htmlCode;

    if (response.ok) {

        button.parentElement.remove();

    } else {
        htmlCode = `<span id="error">${data.error}</span>`;
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});