fetch('/api/communities/show_mine', {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();
        let htmlCode = ``;
        if (response.ok) {
            if (Object.keys(data).length === 0) {
                htmlCode += '<p id="noOne">Вы пока не создали ни одно сообщество</p>';
            } else {
                data.forEach(element => {
                    htmlCode += `<p>
                                    <a href="/community/${element.id}">${element.name}</a>
                                    <button class="deleteButton" id="${element.id}">удалить сообщество</button>
                                </p>`;
                });
            }
        } else {
            htmlCode += `<p>${data.error}</p>`;
        }
        document.getElementById('communities').insertAdjacentHTML('beforeend', htmlCode);
    });