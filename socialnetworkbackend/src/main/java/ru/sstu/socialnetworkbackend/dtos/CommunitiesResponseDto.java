package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

public record CommunitiesResponseDto(
        User member,
        List<Community> communities
) {

}
