body.addEventListener('submit', e => {
    const form = e.target;

    if (form && form.id === "create-comment") {
        e.preventDefault();

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        fetch(`http://localhost:8081/api/v1/photos/create_comment`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
            .then(async response => {

                removeErrorElements();

                const data = await response.json();

                if (response.ok) {

                    renderComment(data, form);

                } else {
                    renderErrors(data, form);
                }

            })

    }

});

function removeErrorElements() {
    let errorElement = document.getElementById("error");
    if (errorElement) {
        errorElement.remove();
    }
}

function renderComment(data, form) {

    const noOneCommentP = document.querySelector('body').querySelector('#noOneComment');

    if (noOneCommentP) {
        noOneCommentP.remove();
    }

    let commentingUser = data.commentingUser;
    let htmlCode = `<div>
                               <p><a href="/profile/${commentingUser.id}">
                                   ${commentingUser.firstName} ${commentingUser.lastName}
                               </a></p>
                               <p>${data.comment}</p>
                               <p>${data.commentingTimeStamp}</p>
                               <button id="${data.id}" class="delete-comment-button">удалить комментарий</button>
                           </div>`;
    commentsDiv.insertAdjacentHTML('afterbegin', htmlCode);
    form.reset();
}

function renderErrors(data, form) {
    let htmlCode = '<div id="error">';
    for (const [key, value] of Object.entries(data)) {
        htmlCode += `<p style="color: red;">${value}</p>`;
    }
    htmlCode += '</div>';
    form.insertAdjacentHTML("afterend", htmlCode);
}