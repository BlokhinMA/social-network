tagsDiv.addEventListener('submit', function (e) {
    const form = e.target;
    if (form && form.id === 'create-tag') {

        e.preventDefault();

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        fetch('/api/photos/create_tag', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
            .then(async response => {

                removeErrorElements();

                const data = await response.json();
                let htmlCode = '';
                if (response.ok) {

                    const tagsEnumP = tagsDiv.querySelector('#tags-enum');

                    htmlCode += `<span>${data.tag}`; // todo: разобраться с запятыми
                    if (isOwner) {
                        htmlCode += ` <button id="${data.id}" class="delete-tag-button">x</button>`;
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
            });

    }
});