const companionId = window.location.pathname.split('/').pop();

async function show(companionId) {
    const response = await fetch(`http://localhost:8081/api/v1/messages/show/${companionId}`, {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {

        const data = await response.json();

        let htmlCode = ``;

        data.forEach((msg) => {
            if (msg.fromUser.id === localStorage.getItem('userId')) {
                htmlCode += `<p style="text-align: right;">${msg.msg}</p>`;
            } else htmlCode += `<p style="text-align: left;">${msg.msg}</p>`;
        });

        htmlCode += `<form id="send-message">
                         <label for="msg">Написать сообщение</label>
                         <input type="text" name="msg" id="msg" required>
                         <input type="hidden" name="toUserId" value="${companionId}">
                         <button>отправить</button>
                     </form>`;

        document.getElementById('messages').insertAdjacentHTML('beforeend', htmlCode);

    }

}

show(companionId);