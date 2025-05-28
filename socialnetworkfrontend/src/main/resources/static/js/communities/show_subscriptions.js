const memberId = window.location.pathname.split('/').pop();

if (memberId === localStorage.getItem("userId")) {
    window.location = '/my_communities';
}

async function showSubscriptions() {
    const response = await fetch(`http://localhost:8081/api/v1/communities/show_subscriptions/${memberId}`, {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();

    const title = document.querySelector('title');
    const header = document.getElementById('header');
    const communitiesDiv = document.getElementById('communities');

    if (response.ok) {

        let member = data.member;
        let textContent = `Сообщества пользователя ${member.firstName} ${member.lastName}`;
        title.textContent = textContent;
        header.textContent = textContent;

        let communities = data.communities;

        let htmlCode = ``;

        if (communities.length === 0) {
            htmlCode += '<p>Пользователь еще не подписался ни на одно сообщество</p>';
        } else {
            communities.forEach((community) => {
                htmlCode += `<p><a href="/community/${community.id}">${community.name}</a></p>`;
            });
        }

        communitiesDiv.innerHTML = htmlCode;

    } else {
        let error = data.error;
        title.textContent = error;
        communitiesDiv.textContent = error;
    }

}

showSubscriptions();