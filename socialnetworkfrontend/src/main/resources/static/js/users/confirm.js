async function confirmRegistration() {
    const token = window.location.pathname.split("/").pop();
    const response = await fetch(`http://localhost:8081/api/v1/registration/confirm/${token}`, {
        method: "PATCH"
    });
    let htmlCode;
    if (response.ok) {
        htmlCode = `<p>Вы успешно подтвердили свой аккаунт!</p>
                    <p><a href="/sign_in">Вход</a></p>`;
    } else {
        const data = await response.json();
        htmlCode = `<p>${data.error}</p>`;
    }
    document.querySelector('body').insertAdjacentHTML('beforeend', htmlCode);
}

confirmRegistration();