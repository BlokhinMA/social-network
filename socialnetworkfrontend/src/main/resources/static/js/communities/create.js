const form = document.getElementById("create");

form.addEventListener("submit", (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    fetch("/api/communities/create", {
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
            let htmlCode = ``;
            if (response.ok) {
                const noOneP = document.getElementById("noOne"); // todo: исправить названия id
                if (noOneP) {
                    noOneP.remove();
                }
                const communitiesDiv = document.getElementById("communities");
                htmlCode += `<p>
                                <a href="/community/${data.id}">${data.name}</a>
                                <button class="deleteButton" id="${data.id}">удалить сообщество</button>
                            </p>`;
                communitiesDiv.insertAdjacentHTML("beforeend", htmlCode);
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
});
