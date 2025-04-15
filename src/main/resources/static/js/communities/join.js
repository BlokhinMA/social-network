const communityFooter = document.getElementById("community-footer");

communityFooter.addEventListener("click", (e) => {
    if (e.target && e.target.id === "subscribe-button") {
        const button = e.target;

        fetch(`/api/communities/join/${communityId}`, {
            method: "GET",
        })
            .then(async response => {
                const data = await response.json();
                let errorElement = document.getElementById('error');
                if (errorElement) {
                    errorElement.remove();
                }
                let htmlCode = '';
                if (response.ok) {
                    const noOneP = document.getElementById('noOneMember');
                    const membersDiv = document.getElementById('members');
                    if (noOneP) {
                        noOneP.remove();
                        htmlCode += '<p>Подписчики</p>'
                        membersDiv.insertAdjacentHTML('afterbegin', htmlCode);
                    }
                    button.textContent = "выйти из сообщества";
                    htmlCode = `<p><a href="/profile/${data.member.id}">${data.member.firstName} ${data.member.lastName}</a>`;
                    const firstChildMembersDiv = membersDiv.childNodes.item(0);
                    firstChildMembersDiv.insertAdjacentHTML('afterend', htmlCode);
                } else {
                    htmlCode += `<span id="error" style="color: red">${data.error}</span>`
                    button.insertAdjacentHTML('afterend', htmlCode);
                }
            });

    }
});