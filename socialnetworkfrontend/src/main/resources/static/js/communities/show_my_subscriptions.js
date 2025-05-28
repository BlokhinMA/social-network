async function showMySubscriptions() {

    const response = await fetch('http://localhost:8081/api/v1/communities/show_my_subscriptions', {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();

    let htmlCode = '';

    if (response.ok) {
        if (Object.keys(data).length === 0) {
            htmlCode += `<p>Вы пока не вступили ни в одно сообщество</p>`;
        } else {
            htmlCode += '<p>Мои подписки</p>';
            data.forEach(element => {
                htmlCode += `<p><a href="/community/${element.id}">${element.name}</a></p>`;
            });
        }
    } else {
        htmlCode += `<p>${data.error}</p>`;
    }
    document.getElementById('communities').insertAdjacentHTML('beforeend', htmlCode);

}

showMySubscriptions();