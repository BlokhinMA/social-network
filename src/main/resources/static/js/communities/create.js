const form = document.getElementById("create");

form.addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(this);
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
            let htmlCode;
            let error;
            if (response.ok) {
                const noOneP = document.getElementById("noOne");
                if (noOneP) {
                    noOneP.remove();
                }
                const communitiesDiv = document.getElementById("communities");
                htmlCode = `<p>
                                <a href="/community/${data.id}">${data.name}</a>
                                <button class="deleteButton" id="${data.id}">удалить сообщество</button>
                            </p>`;
                communitiesDiv.insertAdjacentHTML("beforeend", htmlCode);
                form.reset();
            } else {
                error = data.name;
                htmlCode = `<p id="error">${error}</p>`;
                form.insertAdjacentHTML("afterend", htmlCode);
            }
        });
});
