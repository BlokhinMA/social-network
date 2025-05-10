package ru.sstu.socialnetworkbackend.dtos.communities;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.User;

import java.time.LocalDateTime;

public record CommunityPostResponseDto(
    Long id,
    String postText,
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime creationTimeStamp,
    User author,
    Community community,
    Boolean isAuthor
) {

}
