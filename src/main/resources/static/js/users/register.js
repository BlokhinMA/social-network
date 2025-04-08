const form = document.getElementById('register');

form.addEventListener('submit', function (event) {

    event.preventDefault();

    const formData = new FormData(this);
    const data = Object.fromEntries(formData.entries());

    fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(async response => {
            errorElements = document.querySelectorAll('.error');
            if (errorElements) {
                errorElements.forEach(el => {
                    br = document.createElement('br');
                    el.replaceWith(br);
                });
            }
            const data = await response.json();
            if (response.ok) {
                htmlCode = '<div style="color: green;">Вы успешно зарегистрировались!</div>';
                document.getElementById('sign-in').insertAdjacentHTML('afterend', htmlCode);
            } else {
                for (const [key, value] of Object.entries(data)) {
                    div = document.createElement('div');
                    div.classList.add('error');
                    div.style.color = 'red';
                    div.textContent = value;
                    if (key === 'error') {
                        document.querySelector('button').nextElementSibling.replaceWith(div);
                    } else {
                        document.querySelector(`[name="${key}"]`).nextElementSibling.replaceWith(div);
                    }
                }
            }
        });

});