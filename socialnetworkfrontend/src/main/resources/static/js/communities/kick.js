const membersDiv = document.getElementById('members');

document.addEventListener('click', async (e) => {

    const button = e.target;

    if (!button?.classList.contains('kick-buttons')) return;

    const response = await fetch(`http://localhost:8081/api/v1/communities/kick/${button.id}`, {
        method: 'DELETE',
        credentials: 'include'
    });

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

});