if (localStorage.getItem('userId') === null) {
    window.location = '/sign_in';
}

const button = document.getElementById('button');

button.addEventListener('click', () => {
    const keyword = document.getElementById('keyword').value;
    fetch(`http://localhost:8081/api/v1/communities/find/${keyword}`, {
        method: 'GET',
        credentials: 'include'
    })
        .then(async response => {
            const data = await response.json();
            let htmlCode = ``;
            if (response.ok) {
                const communities = document.getElementById('communities');

                if (data.length === 0) {
                    htmlCode += `<p>Ничего не найдено</p>`;
                } else {
                    data.forEach(community => {
                        htmlCode += `<p><a href="/community/${community.id}">${community.name}</a></p>`;
                    });
                }

                communities.innerHTML = htmlCode;
            } else {
                htmlCode += `<span>${data.error}</span>`;
                button.insertAdjacentHTML('afterend', htmlCode);
            }
        });
});