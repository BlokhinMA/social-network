document.addEventListener('submit', async (e) => {

    e.preventDefault();

    const form = e.target;

    if (form?.id !== 'create-tag') return;

    const formData = new FormData(form);
    const dataFromForm = Object.fromEntries(formData.entries());

    const response = await fetch('http://localhost:8081/api/v1/photos/create_tag', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(dataFromForm)
    });

    removeErrorElements();

    const data = await response.json();
    let htmlCode = '';
    if (response.ok) {

        const noTagsSpan = document.getElementById('no-tags');

        if (noTagsSpan) {
            noTagsSpan.remove();
        }

        const tagsEnumP = document.getElementById('tags-enum');

        htmlCode += `<span>${data.tag}<button id="${data.id}" class="delete-tag">x</button></span>`;

        tagsEnumP.insertAdjacentHTML('beforeend', htmlCode);

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