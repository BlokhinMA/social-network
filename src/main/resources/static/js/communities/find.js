const button = document.getElementById('button');

button.addEventListener('click', function () {
    const word = document.getElementById('word').value;
    fetch(`/api/communities/find/${word}`, {
        method: 'GET'
    })
        .then(async response => {
            const data = await response.json();
            if (response.ok) {
                const communities = document.getElementById('communities');
                while (communities.firstChild) {
                    communities.removeChild(communities.firstChild);
                }
                if (Object.keys(data).length === 0) {
                    htmlCode = `<p>Ничего не найдено</p>`;
                    communities.insertAdjacentHTML('beforeend', htmlCode);
                } else {
                    data.forEach(element => {
                        htmlCode = `<p><a href="/community/${element.id}">${element.name}</a></p>`;
                        communities.insertAdjacentHTML('beforeend', htmlCode);
                    });
                }
            } else {
                console.log(data.error);
            }
        });
});