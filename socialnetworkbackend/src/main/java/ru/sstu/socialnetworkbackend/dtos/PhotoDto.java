package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PhotoDto {

    @NotNull(message = "Поле \"files\" не должно быть null")
    private List<MultipartFile> files;
    @NotNull(message = "Поле \"albumId\" не должно быть null")
    private Long albumId;

    public PhotoDto() {
    }

    public PhotoDto(List<MultipartFile> files, Long albumId) {
        this.files = files;
        this.albumId = albumId;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

}
