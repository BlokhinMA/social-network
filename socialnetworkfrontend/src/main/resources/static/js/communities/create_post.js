document.addEventListener('submit', async (e) => {

    e.preventDefault();

    const form = e.target;

    if (form?.id !== 'create-post') return;

    const formData = new FormData(form);
    const dataFromForm = Object.fromEntries(formData.entries());

    const response = await fetch("http://localhost:8081/api/v1/communities/create_post", {
        method: "POST",
        credentials: 'include',
        headers: {
            "Content-type": "application/json",
        },
        body: JSON.stringify(dataFromForm)
    });

    let errorElement = document.getElementById("error");
    if (errorElement) {
        errorElement.remove();
    }
    const data = await response.json();
    let htmlCode = '';
    if (response.ok) {
        const noPostsP = document.getElementById("no-posts");
        const postsDiv = document.getElementById("posts");
        htmlCode = '<p>Посты</p>';
        if (noPostsP) {
            noPostsP.remove();
            postsDiv.insertAdjacentHTML('afterbegin', htmlCode);
        }
        const firstChildPostsDiv = postsDiv.childNodes.item(0);
        const author = data.author;
        htmlCode = `<div id="${data.id}">
                            <p>
                                <a href="/profile/${author.id}">
                                    ${data.author.firstName} ${author.lastName}
                                </a>
                            </p>
                            <p>${data.postText}</p>
                            <p>${data.creationTimeStamp}</p>
                            <button id="${data.id}" class="delete-post-button">удалить пост</button>
                        </div>`;
        firstChildPostsDiv.insertAdjacentHTML("afterend", htmlCode);
        form.reset();
    } else {
        htmlCode += '<div id="error">';
        for (const value of Object.values(data)) {
            htmlCode += `<p style="color: red;">${value}</p>`;
        }
        htmlCode += '</div>';
        form.insertAdjacentHTML("afterend", htmlCode);
    }

});