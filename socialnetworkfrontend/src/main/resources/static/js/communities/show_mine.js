async function showMine() {
    const response = await fetch('http://localhost:8081/api/v1/communities/show_mine', {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();
    let htmlCode = ``;
    if (response.ok) {
        if (Object.keys(data).length === 0) {
            htmlCode += '<p id="no-communities">Вы пока не создали ни одно сообщество</p>';
        } else {
            data.forEach(element => {
                htmlCode += `<p>
                                 <a href="/community/${element.id}">${element.name}</a>
                                 <button class="delete-community-buttons" id="${element.id}">удалить сообщество</button>
                             </p>`;
            });
        }
    } else {
        htmlCode += `<p>${data.error}</p>`;
    }
    document.getElementById('communities').insertAdjacentHTML('beforeend', htmlCode);

}

showMine();