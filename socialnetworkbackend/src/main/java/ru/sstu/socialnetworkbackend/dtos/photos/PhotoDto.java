package ru.sstu.socialnetworkbackend.dtos.photos;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PhotoDto(
    @NotNull(message = "Поле \"files\" должно быть заполнено")
    List<MultipartFile> files,
    @NotNull(message = "Поле \"albumId\" должно быть заполнено")
    Long albumId
) {

}
