document.addEventListener('click', async (e) => {

    const rating = document.querySelector('.rating');
    const button = e.target;

    if (!button?.classList.contains('rating-buttons')) return;

    const data = {
        "rating": button.id,
        "photoId": photoId
    };

    let userRating = await getRating(photoId);

    switch (userRating) {
        case true:
            if (data.rating === 'true') {
                await deleteRating(rating, button);
            }
            if (data.rating === 'false') {
                await updateRating(rating, button, data, 'red');
            }
            break;
        case false:
            if (data.rating === 'true') {
                await updateRating(rating, button, data, 'green');
            }
            if (data.rating === 'false') {
                await deleteRating(rating, button);
            }
            break;
        case null:
            await createRating(rating, button, data);
    }
});

async function createRating(rating, button, requestData) {

    removeErrorElements();

    const response = await fetch('http://localhost:8081/api/v1/photos/create_rating', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    });

    const data = await response.json();

    let htmlCode;

    if (response.ok) {

        button.style.backgroundColor = data.photoRating.rating === true
            ? 'green'
            : 'red';

        if (rating.id === 'no-rating') {
            rating.id = 'rating';
        }

        rating.textContent = `Рейтинг: ${data.rating}%`;

    } else {
        htmlCode = `<p>${data.error}</p>`;
        button.insertAdjacentHTML('afterbegin', htmlCode);
    }

}

async function updateRating(rating, button, requestData, flag) {

    removeErrorElements();

    const response = await fetch('http://localhost:8081/api/v1/photos/update_rating', {
        method: 'PATCH',
        credentials: 'include',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(requestData)
    });

    const data = await response.json();

    if (response.ok) {
        button.style.backgroundColor = flag;
        let id = flag === 'red'
            ? '#true'
            : '#false';
        ratingsDiv.querySelector(id).style.backgroundColor = '';
        rating.textContent = `Рейтинг: ${data.rating}%`;
    } else {
        let htmlCode = `<span id="error" style="color: red;">${data.error}</span>`;
        button.insertAdjacentHTML('afterend', htmlCode);
    }

}

async function deleteRating(rating, button) {

    removeErrorElements();

    const response = await fetch(`http://localhost:8081/api/v1/photos/delete_rating/${photoId}`, {
        method: 'DELETE',
        credentials: 'include'
    });

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

}

async function getRating(photoId) {
    const response = await fetch(`http://localhost:8081/api/v1/photos/user_rating/${photoId}`, {
        method: 'GET',
        credentials: 'include'
    });
    const data = await response.json();
    if (response.ok) {
        return data.userRating;
    }
}