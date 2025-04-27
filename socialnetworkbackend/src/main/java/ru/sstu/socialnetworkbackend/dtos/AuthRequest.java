package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.NotNull;

public class AuthRequest {

    @NotNull(message = "Поле \"Логин\" не должно быть null")
    private String username;
    @NotNull(message = "Поле \"Пароль\" не должно быть null")
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
