ratingsDiv.addEventListener('click', async (e) => {
    const rating = ratingsDiv.querySelector('.rating');
    if (e.target && e.target.classList.contains('rating-buttons')) {
        const button = e.target;

        const data = {
            "rating": button.id,
            "photoId": photoId
        };

        let userRating = await getRating(photoId);

        switch (userRating) {
            case true:
                if (data.rating === 'true') {
                    deleteRating(rating, button);
                }
                if (data.rating === 'false') {
                    updateRating(rating, button, data, 'red');
                }
                break;
            case false:
                if (data.rating === 'true') {
                    updateRating(rating, button, data, 'green');
                }
                if (data.rating === 'false') {
                    deleteRating(rating, button);
                }
                break;
            case null:
                createRating(rating, button, data);
        }
    }
});

function createRating(rating, button, data) {
    let errorElement = document.getElementById("error");
    if (errorElement) {
        errorElement.remove();
    }
    fetch('/api/photos/create_rating', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(async response => {
            const data = await response.json();

            let htmlCode;

            if (response.ok) {

                if (data.photoRating.rating === true) {
                    button.style.backgroundColor = 'green';
                }

                if (data.photoRating.rating === false) {
                    button.style.backgroundColor = 'red';
                }

                if (rating.id === 'no-rating') {
                    rating.id = 'rating';
                }

                rating.textContent = `Рейтинг: ${data.rating}%`;

            } else {
                htmlCode = `<p>${data.error}</p>`;
                button.insertAdjacentHTML('afterbegin', htmlCode);
            }
        });
}

function updateRating(rating, button, data, flag) {
    let errorElement = document.getElementById("error");
    if (errorElement) {
        errorElement.remove();
    }
    fetch('/api/photos/update_rating', {
        method: 'PATCH',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(async response => {
            const data = await response.json();

            if (response.ok) {
                button.style.backgroundColor = flag;
                let id;
                if (flag === 'red') {
                    id = '#true';
                }
                if (flag === 'green') {
                    id = '#false';
                }
                ratingsDiv.querySelector(id).style.backgroundColor = '';
                rating.textContent = `Рейтинг: ${data.rating}%`;
            } else {
                let htmlCode = `<span id="error" style="color: red;">${data.error}</span>`;
                button.insertAdjacentHTML('afterend', htmlCode);
            }
        });
}

function deleteRating(rating, button) {
    let errorElement = document.getElementById("error");
    if (errorElement) {
        errorElement.remove();
    }
    fetch(`/api/photos/delete_rating/${photoId}`, {
        method: 'DELETE'
    })
        .then(async response => {
            const data = await response.json();

            if (response.ok) {

                button.style.backgroundColor = '';

                const ratingFromServer = data.rating;
                if (ratingFromServer != null) {
                    rating.textContent = `Рейтинг: ${ratingFromServer}%`;
                } else {
                    rating.textContent = 'Фотография еще не оценивалась';
                    rating.id = 'no-rating';
                }
            } else {
                let htmlCode = `<span id="error" style="color: red;">${data.error}</span>`;
                button.insertAdjacentHTML('afterend', htmlCode);
            }

        });
}

async function getRating(photoId) {
    const response = await fetch(`/api/photos/user_rating/${photoId}`, {
        method: 'GET'
    });
    const data = await response.json();
    if (response.ok) {
        return data.userRating;
    }
}