const socket = new SockJS('http://localhost:8081/chat', null, {
    transports: ['websocket'],
    xhrFields: {
        withCredentials: true
    }
});
const stompClient = Stomp.over(socket);

stompClient.connect({}, function () {
    stompClient.subscribe('/topic/messages', function (message) {
        const msg = JSON.parse(message.body);

        let htmlCode;

        if (msg.fromUser.id === localStorage.getItem('userId')) {
            htmlCode = `<p style="text-align: right;">${msg.msg}</p>`;
        } else htmlCode = `<p style="text-align: left;">${msg.msg}</p>`;

        document.getElementById('messages').insertAdjacentHTML('beforeend', htmlCode);
    });
});

document.addEventListener('submit', (e) => {
    e.preventDefault();

    const form = e.target;

    if (form?.getAttribute('id') !== 'send-message') return;

    const formData = new FormData(form);
    const dataFromForm = Object.fromEntries(formData.entries());

    stompClient.send('http://localhost:8081/app/sendMessage', {}, JSON.stringify(dataFromForm));

})