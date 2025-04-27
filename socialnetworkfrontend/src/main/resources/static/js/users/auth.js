const form = document.querySelector('form');

form.addEventListener('submit', (e) => {

    e.preventDefault();

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    fetch('/api/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(async response => {
            if (response.ok) {
                window.location = '/my_profile';
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