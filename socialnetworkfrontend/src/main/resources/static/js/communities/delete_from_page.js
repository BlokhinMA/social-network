document.querySelector('body').addEventListener('click', (e) => {

    const button = e.target;

    if (button && button.id === 'delete-community-button') {

        fetch(`http://localhost:8081/api/v1/communities/delete/${communityId}`, {
            method: 'DELETE',
            credentials: 'include'
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