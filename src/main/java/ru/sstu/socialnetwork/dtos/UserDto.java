package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotNull(message = "Поле \"Логин\" не должно быть null")
    @Size(min = 1, max = 255, message = "Поле \"Логин\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Логин\" не должно содержать пробелы")
    private String login;
    @NotNull(message = "Поле \"Email\" не должно быть null")
    @Size(min = 1, max = 255, message = "Поле \"Email\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Email\" не должно содержать пробелы")
    @Email
    private String email;
    @NotNull(message = "Поле \"Имя\" не должно быть null")
    @Size(min = 1, max = 255, message = "Поле \"Имя\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Имя\" не должно содержать пробелы")
    private String firstName;
    @NotNull(message = "Поле \"Фамилия\" не должно быть null")
    @Size(min = 1, max = 255, message = "Поле \"Фамилия\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Фамилия\" не должно содержать пробелы")
    private String lastName;
    @NotNull(message = "Поле \"Дата рождения\" не должно быть null")
    //@PastOrPresent(message = "Указанная дата должна быть либо в прошлом, либо в настоящем (сегодняшняя)")
    @Pattern(regexp = "(\\d{4})-(0[1-9]|1[0-2]|[1-9])-([1-9]|0[1-9]|[1-2]\\d|3[0-1])",message = "Указанная дата должна быть корректной в формате гггг-мм-дд")
    private String birthDate;
    @NotNull(message = "Поле \"Пароль\" не должно быть null")
    @Size(min = 1, max = 255, message = "Поле \"Пароль\" должно содержать минимум 1 и максимум 255 символов")
    private String password;
    @NotNull(message = "Поле \"Подтвержденный пароль\" не должно быть null")
    private String confirmedPassword;

    public UserDto() {
    }

    public UserDto(String login, String email, String firstName, String lastName, String birthDate, String password, String confirmedPassword) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

}
