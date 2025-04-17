fetch('/api/communities/show_my_subscriptions', {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();
        if (response.ok) {
            const communitiesDiv = document.getElementById('communities');
            let htmlCode = '';
            if (Object.keys(data).length === 0) {
                htmlCode += `<p>Вы пока не вступили ни в одно сообщество</p>`;
            } else {
                htmlCode += '<p>Мои подписки</p>';
                data.forEach(element => {
                    htmlCode += `<p><a href="/community/${element.id}">${element.name}</a></p>`;
                });
            }
            communitiesDiv.insertAdjacentHTML('beforeend', htmlCode);
        } else {
            console.log(data.error);
        }
    });