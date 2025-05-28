async function show_all() {
    const response = await fetch('http://localhost:8081/api/v1/messages/show_all', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();

        let htmlCode = ``;

        data.forEach((dialogue) => {
            const companion = dialogue.companion;
            htmlCode += `<p>
                             <span>${companion.firstName} ${companion.lastName}</span>
                             <a href="/messages/${companion.id}">
                                 ${dialogue.lastMsg.msg}
                             </a>
                         </p>`;
        });

        document.querySelector('body').insertAdjacentHTML('beforeend', htmlCode);

    }

}

show_all();