document.querySelector('body').addEventListener('click', (e) => {

    const button = e.target;

    if (button && button.id === 'delete-community-button') {

        fetch(`/api/communities/delete/${communityId}`, {
            method: 'DELETE'
        })
            .then(async response => {
                if (response.ok) {
                    window.location = '/community_management';
                } else {
                    const data = await response.json();
                    let htmlCode = `<span>${data.error}</span>`;
                    button.insertAdjacentHTML('afterend', htmlCode);
                }
            });
    }

});