document.addEventListener('submit', async (e) => {

    e.preventDefault();

    const form = e.target;

    if (form?.id !== "create-comment") return;

    const formData = new FormData(form);
    const dataFromForm = Object.fromEntries(formData.entries());

    const response = await fetch(`http://localhost:8081/api/v1/photos/create_comment`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(dataFromForm)
    });

    removeErrorElements();

    const data = await response.json();

    if (response.ok) {

        renderComment(data, form);

    } else {
        renderErrors(data, form);
    }

});

function removeErrorElements() {
    let errorElement = document.getElementById("error");
    if (errorElement) {
        errorElement.remove();
    }
}

function renderComment(data, form) {

    const noOneCommentP = document.getElementById('noOneComment');

    if (noOneCommentP) {
        noOneCommentP.remove();
    }

    let commentingUser = data.commentingUser;
    let htmlCode = `<div>
                               <p>
                               <a href="/profile/${commentingUser.id}">
                                   ${commentingUser.firstName} ${commentingUser.lastName}
                               </a>
                               </p>
                               <p>${data.comment}</p>
                               <p>${data.commentingTimeStamp}</p>
                               <button id="${data.id}" class="delete-comment-button">удалить комментарий</button>
                           </div>`;
    commentsDiv.insertAdjacentHTML('afterbegin', htmlCode);
    form.reset();
}

function renderErrors(data, form) {
    let htmlCode = '<div id="error">';
    for (const value of Object.values(data)) {
        htmlCode += `<p style="color: red;">${value}</p>`;
    }
    htmlCode += '</div>';
    form.insertAdjacentHTML("afterend", htmlCode);
}