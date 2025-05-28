const form = document.getElementById("create");

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    const dataFromForm = Object.fromEntries(formData.entries());

    const response = await fetch("http://localhost:8081/api/v1/communities/create", {
        method: 'POST',
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
    let htmlCode = ``;
    if (response.ok) {
        const noCommunitiesP = document.getElementById("no-communities");
        if (noCommunitiesP) {
            noCommunitiesP.remove();
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
        for (const value of Object.values(data)) {
            htmlCode += `<p style="color: red;">${value}</p>`;
        }
        htmlCode += '</div>';
        form.insertAdjacentHTML("afterend", htmlCode);
    }

});
