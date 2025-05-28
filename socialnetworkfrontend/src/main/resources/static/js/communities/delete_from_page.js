document.addEventListener('click', async (e) => {

    const button = e.target;

    if (button?.id !== 'delete-community-button') return;

    const response = await fetch(`http://localhost:8081/api/v1/communities/delete/${communityId}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    if (response.ok) {
        window.location = '/community_management';
    } else {
        const data = await response.json();
        let htmlCode = `<span>${data.error}</span>`;
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});