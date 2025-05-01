document.querySelector('body').addEventListener('click', (e) => {

    const button = e.target;

    if (button && button.classList.contains('make-friend')) {

        fetch(`http://localhost:8081/api/v1/friendships/create/${button.id}`, {
            method: 'GET',
            credentials: 'include'
        })
            .then(async response => {

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

    }

});