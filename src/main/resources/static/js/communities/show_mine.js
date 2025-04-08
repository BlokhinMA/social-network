fetch('/api/communities/show_mine', {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();
        if (response.ok) {
            const communitiesDiv = document.getElementById('communities');
            if (Object.keys(data).length === 0) {
                htmlCode = '<p id="noOne">Вы пока не создали ни одно сообщество</p>';
                communitiesDiv.insertAdjacentHTML('beforeend', htmlCode);
            } else {
                data.forEach(element => {
                    htmlCode = `<p><a href="/community/${element.id}">${element.name}</a><button class="deleteButton" id="${element.id}">удалить сообщество</button></p>`;
                    communitiesDiv.insertAdjacentHTML('beforeend', htmlCode);
                });
            }
        } else {
            console.log(data.error);
        }
    });