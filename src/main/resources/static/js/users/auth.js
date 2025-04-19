const form = document.querySelector('form');

form.addEventListener('submit', (e) => {

    e.preventDefault();

    const formData = new FormData(this);
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
                console.log(data.error);
            }
        });

});