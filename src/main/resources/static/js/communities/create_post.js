const body = document.querySelector('body');

body.addEventListener('submit', function (event) {
    if (event.target && event.target.id === 'createPost') {

        const form = event.target;

        event.preventDefault();

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        fetch("/api/communities/create_post", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
            },
            body: JSON.stringify(data),
        })
            .then(async (response) => {
                let errorElement = document.getElementById("error");
                if (errorElement) {
                    errorElement.remove();
                }
                const data = await response.json();
                let htmlCode = '';
                if (response.ok) {
                    const noOneP = document.getElementById("noOnePost");
                    if (noOneP) {
                        noOneP.remove();
                    }
                    const postsDiv = document.getElementById("posts");
                    htmlCode += `<div id="${data.id}">
                                    <p><a href="/profile/${data.author.id}">${data.author.firstName} ${data.author.lastName}</a></p>
                                    <p>${data.postText}</p>
                                    <p>${data.creationTimeStamp}</p>
                                    <button id="${data.id}" class="delete-post-button">удалить пост</button>
                                </div>`;
                    postsDiv.insertAdjacentHTML("afterbegin", htmlCode);
                    form.reset();
                } else {
                    htmlCode += '<div id="error">'
                    for (const [key, value] of Object.entries(data)) {
                        htmlCode += `<p style="color: red">${value}</p>`;
                    }
                    htmlCode += '</div>';
                    form.insertAdjacentHTML("afterend", htmlCode);
                }
            });
    }
});