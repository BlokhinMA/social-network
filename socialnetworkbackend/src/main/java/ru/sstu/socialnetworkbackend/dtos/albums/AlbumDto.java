package ru.sstu.socialnetworkbackend.dtos.albums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AlbumDto(
    @Size(min = 1, max = 255, message = "Поле \"Название\" должно содержать минимум 1 и максимум 255 символов")
    @NotBlank(message = "Поле \"Название\" не должно состоять только из пробелов")
    String title,
    @NotNull(message = "Поле \"Тип доступа\" должно быть заполнено")
    @Pattern(regexp = "ALL|FRIENDS", message = "Значение должно быть равным \"ALL\" или \"FRIENDS\"")
    String accessType,
    List<MultipartFile> files
) {

}
