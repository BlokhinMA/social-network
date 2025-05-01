const body = document.querySelector('body');

body.addEventListener('submit', (e) => {

    const form = e.target;

    if (form && form.id === 'create-post') {

        e.preventDefault();

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        fetch("http://localhost:8081/api/v1/communities/create_post", {
            method: "POST",
            credentials: 'include',
            headers: {
                "Content-type": "application/json",
            },
            body: JSON.stringify(data)
        })
            .then(async (response) => {
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
                    htmlCode = `<div id="${data.id}">
                                    <p><a href="/profile/${data.author.id}">${data.author.firstName} ${data.author.lastName}</a></p>
                                    <p>${data.postText}</p>
                                    <p>${data.creationTimeStamp}</p>
                                    <button id="${data.id}" class="delete-post-button">удалить пост</button>
                                </div>`;
                    firstChildPostsDiv.insertAdjacentHTML("afterend", htmlCode);
                    form.reset();
                } else {
                    htmlCode += '<div id="error">';
                    for (const [key, value] of Object.entries(data)) {
                        htmlCode += `<p style="color: red;">${value}</p>`;
                    }
                    htmlCode += '</div>';
                    form.insertAdjacentHTML("afterend", htmlCode);
                }
            });
    }
});