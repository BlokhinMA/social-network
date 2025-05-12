package ru.sstu.socialnetworkbackend.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto(
    @NotNull(message = "Поле \"Логин\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Логин\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Логин\" не должно содержать пробелы")
    String username,
    @NotNull(message = "Поле \"Email\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Email\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Email\" не должно содержать пробелы")
    @Email
    String email,
    @NotNull(message = "Поле \"Имя\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Имя\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Имя\" не должно содержать пробелы")
    String firstName,
    @NotNull(message = "Поле \"Фамилия\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Фамилия\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Фамилия\" не должно содержать пробелы")
    String lastName,
    @NotNull(message = "Поле \"Дата рождения\" должно быть заполнено")
    @Pattern(regexp = "(\\d{4})-(0[1-9]|1[0-2]|[1-9])-([1-9]|0[1-9]|[1-2]\\d|3[0-1])", message = "Указанная дата " +
        "должна быть корректной и в формате гггг-мм-дд")
    String birthDate,
    @NotNull(message = "Поле \"Пароль\" должно быть заполнено")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,255}$",
        message = "Пароль должен иметь хотя бы одну строчную, заглавную букву и цифру, " +
            "хотя бы один спецсимвол (@$!%*?&), " +
            "только латинские строчные и заглавные буквы, цифры и спецсимволы, " +
            "минимум 8 и максимум 255 символов")
    String password,
    @NotNull(message = "Поле \"Подтвержденный пароль\" должно быть заполнено")
    String confirmedPassword
) {

}
