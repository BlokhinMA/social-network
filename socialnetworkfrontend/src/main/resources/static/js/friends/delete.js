const body = document.querySelector('body');

body.addEventListener('click', (e) => {

    const button = e.target;

    if (button && button.classList.contains('delete-friend')) {

        fetch(`/api/friendships/delete/${button.id}`, {
            method: 'DELETE'
        })
            .then(async response => {

                let errorElement = document.getElementById("error");
                if (errorElement) {
                    errorElement.remove();
                }

                let htmlCode;

                if (response.ok) {
                    button.parentElement.remove();

                    if (myFriendsDiv.children.length === 0) {
                        htmlCode = '<p id="no-friends">Вы еще не добавили друзей</p>';
                        document.getElementById('my-friends').insertAdjacentHTML('afterbegin', htmlCode);
                    }

                } else {
                    const data = await response.json();
                    htmlCode = `<span style="color: red;">${data.error}</span>`;
                    button.insertAdjacentHTML('afterend', htmlCode);
                }

            });

    }

});