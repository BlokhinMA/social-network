const form = document.querySelector('form');

form.addEventListener('submit', async (e) => {

    e.preventDefault();

    const formData = new FormData(form);
    const dataFromForm = Object.fromEntries(formData.entries());

    const response = await fetch('http://localhost:8081/api/v1/auth', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dataFromForm)
    });

    const errorElements = document.querySelectorAll('.error');
    if (errorElements) {
        errorElements.forEach(el => {
            let br = document.createElement('br');
            el.replaceWith(br);
        });
    }

    const data = await response.json();
    if (response.ok) {
        localStorage.setItem('userId', data.id);
        window.location = '/my_profile';
    } else {
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