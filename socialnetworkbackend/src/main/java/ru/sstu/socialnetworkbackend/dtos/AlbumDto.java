package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AlbumDto {

    @Size(min = 1, max = 255, message = "Поле \"Название\" должно содержать минимум 1 и максимум 255 символов")
    @NotBlank(message = "Поле \"Название\" не должно состоять только из пробелов")
    private String title;
    @NotNull(message = "Поле \"Тип доступа\" не должно быть null")
    @Pattern(regexp = "ALL|FRIENDS", message = "Значение должно быть равным \"ALL\" или \"FRIENDS\"")
    private String accessType;
    private List<MultipartFile> files;

    public AlbumDto() {
    }

    public AlbumDto(String title, String accessType, List<MultipartFile> files) {
        this.title = title;
        this.accessType = accessType;
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

}
