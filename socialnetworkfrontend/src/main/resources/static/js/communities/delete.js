const communitiesDiv = document.getElementById('communities');

document.addEventListener('click', async (e) => {
    const button = e.target;

    if (!button?.classList.contains('delete-community-buttons')) return;

    const response = await fetch(`http://localhost:8081/api/v1/communities/delete/${button.id}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    let errorElement = document.getElementById('error');
    if (errorElement) {
        errorElement.remove();
    }
    let htmlCode;
    if (response.ok) {
        const p = button.parentElement;
        p.remove();
        if (communitiesDiv.children.length === 0) {
            htmlCode = `<p id="no-communities">Вы пока не создали ни одно сообщество</p>`;
            communitiesDiv.insertAdjacentHTML('beforeend', htmlCode);
        }
    } else {
        const data = await response.json();
        htmlCode = `<span id="error" style="color: red;">${data.error}</span>`
        button.insertAdjacentHTML('afterend', htmlCode);
    }

});