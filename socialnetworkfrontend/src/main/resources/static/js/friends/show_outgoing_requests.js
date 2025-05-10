const outgoingRequestsDiv = document.getElementById('outgoing-requests');

async function showOutgoingRequests() {
    const response = await fetch('http://localhost:8081/api/v1/friendships/show_outgoing_requests', {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();

    let htmlCode;

    if (response.ok) {

        if (data.length === 0) {
            htmlCode = '<p>Вы пока не отправили никому заявку</p>';
        } else {
            let id;
            htmlCode = '';
            data.forEach((request) => {
                id = request.id;
                htmlCode += `<p>
                                 <a href="/profile/${id}">${request.firstName} ${request.lastName}</a>
                                 <button class="delete-outgoing-request" id="${id}">удалить заявку</button>
                             </p>`;
            });
        }

    } else {
        htmlCode = `<p>${data.error}</p>`;
    }

    outgoingRequestsDiv.insertAdjacentHTML('afterbegin', htmlCode);

}

showOutgoingRequests();