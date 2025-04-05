fetch('/api/communities/show_my_subscriptions', {
    method: 'GET'
})
    .then(async response => {
        if (!response.ok) {
            const data = await response.json();
            throw new Error(`Ошибка ${response.status}: ${data.error}`);
        }
        return response.json();
    })
    .then(data => {
        const communities = document.getElementById('communities');
        if (Object.keys(data).length === 0) {
            htmlCode = `<p id="noOne">Вы пока не вступили ни в одно сообщество</p>`;
            communities.insertAdjacentHTML('beforeend', htmlCode);
          } else {
            data.forEach(element => {
                htmlCode = `<p><a href="/community/${element.id}">${element.name}</a></p>`;
                communities.insertAdjacentHTML('beforeend', htmlCode);
            });
          }
    });