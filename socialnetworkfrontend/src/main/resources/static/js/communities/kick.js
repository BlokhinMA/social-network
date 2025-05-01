const membersDiv = document.getElementById('members');

membersDiv.addEventListener('click', e => {
    if (e.target && e.target.classList.contains('kick-buttons')) {
        const button = e.target;
        const id = button.id;

        fetch(`http://localhost:8081/api/v1/communities/kick/${id}`, {
            method: 'DELETE',
            credentials: 'include'
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
                    if (membersDivChildren.length === 0) {
                        htmlCode = `<p id="no-members">Нет подписчиков</p>`;
                        membersDiv.insertAdjacentHTML('beforeend', htmlCode);
                    }

                } else {
                    htmlCode = `<span id="error" style="color: red;">${data.error}</span>`;
                    button.insertAdjacentHTML('afterend', htmlCode);
                }

            })
    }
});