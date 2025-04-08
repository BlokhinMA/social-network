const communityId = window.location.pathname.split('/').pop();

fetch(`api/communities/show/${communityId}`, {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();
        if (response.ok) {
            communityName = data.name;
            document.getElementById('communty-name').innerText = communityName;
            document.querySelector('title').innerText = communityName;
        }
    });

fetch(`api/communities/show_all_members/${communityId}`, {
    method: 'GET'
})
    .then(async response => {
        const data = await response.json();
        if (response.ok) {
            membersDiv = document.getElementById('members');
            htmlCode = '';
            if (Object.keys(data).length === 0) {
                htmlCode += '<p>Нет подписчиков</p>';
            } else {
                data.forEach(element => {
                    htmlCode += `<p><a href="/profile/${data.member.id}">${data.member.firstName} ${data.member.lastName}</a></p>`;
                });
            }
            membersDiv.insertAdjacentHTML('beforeend', htmlCode);
        }
    });

// fetch(`api/communities/show_all_posts/${communityId}`, {
//     method: 'GET'
// })
//     .then(async response => {
//         const data = await response.json();
//         if (response.ok) {
//             postsDiv = document.getElementById('posts');
//             htmlCode = '';
//             if (Object.keys(data).length === 0) {
//                 htmlCode += '<p>Нет постов</p>';
//             } else {
//                 data.forEach(element => {
//                     htmlCode += `<p><a href="/profile/${data.id}">${data.login}</a></p>`;
//                 });
//             }
//             postsDiv.insertAdjacentHTML('beforeend', htmlCode);
//         }
//     });