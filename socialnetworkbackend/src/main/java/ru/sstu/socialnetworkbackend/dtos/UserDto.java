package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotNull(message = "Поле \"Логин\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Логин\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Логин\" не должно содержать пробелы")
    private String username;
    @NotNull(message = "Поле \"Email\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Email\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Email\" не должно содержать пробелы")
    @Email
    private String email;
    @NotNull(message = "Поле \"Имя\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Имя\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Имя\" не должно содержать пробелы")
    private String firstName;
    @NotNull(message = "Поле \"Фамилия\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Фамилия\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Фамилия\" не должно содержать пробелы")
    private String lastName;
    @NotNull(message = "Поле \"Дата рождения\" должно быть заполнено")
    //@PastOrPresent(message = "Указанная дата должна быть либо в прошлом, либо в настоящем (сегодняшняя)") todo: сделать
    @Pattern(regexp = "(\\d{4})-(0[1-9]|1[0-2]|[1-9])-([1-9]|0[1-9]|[1-2]\\d|3[0-1])",message = "Указанная дата должна быть корректной и в формате гггг-мм-дд")
    private String birthDate;
    @NotNull(message = "Поле \"Пароль\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Пароль\" должно содержать минимум 1 и максимум 255 символов")
    private String password;
    @NotNull(message = "Поле \"Подтвержденный пароль\" должно быть заполнено")
    private String confirmedPassword;

    public UserDto() {
    }

    public UserDto(String username, String email, String firstName, String lastName, String birthDate, String password, String confirmedPassword) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
