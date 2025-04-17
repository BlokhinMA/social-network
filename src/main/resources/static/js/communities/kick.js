const membersDiv = document.getElementById('members');

membersDiv.addEventListener('click', e => {
    if (e.target && e.target.classList.contains('kick-button')) {
        const button = e.target;
        const id = button.id;

        fetch(`/api/communities/kick/${id}`, {
            method: 'DELETE'
        })
            .then(async response => {
                const data = await response.json();

                let errorElement = document.getElementById('error');
                if (errorElement) {
                    errorElement.remove();
                }

                let htmlCode;
                if (response.ok) {
                    button.parentNode.remove();

                    const membersDivChildren = membersDiv.childNodes;
                    if (membersDivChildren.length === 1) {
                        membersDivChildren.item(0).remove();
                        htmlCode = `<p id="noOneMember">Нет подписчиков</p>`;
                        membersDiv.insertAdjacentHTML('beforeend', htmlCode);
                    }

                } else {
                    htmlCode = `<span id="error" style="color: red;">${data.error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                }

            })
    }
});