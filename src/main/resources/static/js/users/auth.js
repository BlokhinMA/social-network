const form = document.querySelector('form');

form.addEventListener('submit', function (event) {

    event.preventDefault();

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
            const data = await response.json();
            if (response.ok) {
                window.location = '/my_profile';
            } else {
                console.log(data.error);
            }
        });

});