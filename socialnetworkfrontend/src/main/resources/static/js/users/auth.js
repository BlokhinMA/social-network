if (localStorage.getItem('userId') !== null) {
    window.location = '/my_profile';
}

const form = document.querySelector('form');

form.addEventListener('submit', (e) => {

    e.preventDefault();

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    fetch('http://localhost:8081/api/v1/auth', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(async response => {
            const data = await response.json();
            if (response.ok) {
                localStorage.setItem('userId', data.user.id);
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

});