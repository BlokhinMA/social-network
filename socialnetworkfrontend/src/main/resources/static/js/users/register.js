if (localStorage.getItem('userId') !== null) {
    window.location = '/my_profile';
}

const form = document.getElementById('register');

form.addEventListener('submit', (e) => {

    e.preventDefault();

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    fetch('http://localhost:8081/api/v1/registration/register', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(async response => {
            const errorElements = document.querySelectorAll('.error');
            if (errorElements) {
                errorElements.forEach(el => {
                    let br = document.createElement('br');
                    el.replaceWith(br);
                });
            }
            if (response.ok) {
                let htmlCode = '<div style="color: green;">Вы успешно зарегистрировались! ' +
                    'Для подтверждения аккаунта перейдите по ссылке, отправленной на указанную почту</div>';
                document.getElementById('sign-in').insertAdjacentHTML('afterend', htmlCode);
            } else {
                const data = await response.json();
                for (const [key, value] of Object.entries(data)) {
                    let div = document.createElement('div');
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