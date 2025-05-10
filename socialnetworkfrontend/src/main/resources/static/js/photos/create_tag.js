body.addEventListener('submit', async (e) => {
    const form = e.target;
    if (form && form.id === 'create-tag') {

        e.preventDefault();

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

            const noTagsSpan = body.querySelector('#no-tags');

            if (noTagsSpan) {
                noTagsSpan.remove();
            }

            const tagsEnumP = tagsDiv.querySelector('#tags-enum');

            htmlCode += `<span>${data.tag}`; // todo: разобраться с запятыми
            if (isOwner) {
                htmlCode += ` <button id="${data.id}" class="delete-tag">x</button>`;
            }
            htmlCode += `</span>`;

            tagsEnumP.insertAdjacentHTML('beforeend', htmlCode);

            form.reset();

        } else {
            htmlCode += '<div id="error">';
            for (const [key, value] of Object.entries(data)) {
                htmlCode += `<p style="color: red;">${value}</p>`;
            }
            htmlCode += '</div>';
            form.insertAdjacentHTML("afterend", htmlCode);
        }

    }
});