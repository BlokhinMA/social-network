package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageDto {

    private Long fromUserId;
    private Long toUserId;
    @NotBlank(message = "Поле \"Сообщение\" не должно состоять только из пробелов")
    @Size(min = 1, message = "Поле \"Сообщение\" должно содержать минимум 1 символ")
    private String message;

    public MessageDto() {
    }

    public MessageDto(Long fromUserId, Long toUserId, String message) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
