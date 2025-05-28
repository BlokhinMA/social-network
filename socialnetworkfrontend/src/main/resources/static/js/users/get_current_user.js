async function get_current_user() {
    const response = await fetch('http://localhost:8081/api/v1/users/get_current_user', {
        method: 'GET',
        credentials: 'include'
    });

    const data = await response.json();

    let htmlCode = `<h1>${data.firstName} ${data.lastName}</h1>`

    document.querySelector('body').insertAdjacentHTML('beforeend', htmlCode);

}

get_current_user();