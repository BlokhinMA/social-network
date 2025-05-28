const incomingRequestsDiv = document.getElementById('incoming-requests');

async function showIncomingRequests() {

    const response = await fetch('http://localhost:8081/api/v1/friendships/show_incoming_requests', {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();

    let htmlCode;

    if (response.ok) {

        if (data.length === 0) {
            htmlCode = '<p>Входящих заявок пока нет</p>';
        } else {
            let id;
            htmlCode = '';
            data.forEach((request) => {
                id = request.id;
                htmlCode += `<p>
                                 <a href="/profile/${id}">${request.firstName} ${request.lastName}</a>
                                 <button class="accept-friend-buttons" id="${id}">принять заявку</button>
                                 <button class="reject-friend-buttons" id="${id}">отклонить заявку</button>
                             </p>`;
            });
        }

    } else {
        htmlCode = `<p>${data.error}</p>`;
    }

    incomingRequestsDiv.insertAdjacentHTML('afterbegin', htmlCode);

}

showIncomingRequests();