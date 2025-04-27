package ru.sstu.socialnetworkbackend.services;

import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.UserDto;
import ru.sstu.socialnetworkbackend.entities.ConfirmationToken;
import ru.sstu.socialnetworkbackend.entities.User;

@Service
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    public RegistrationService(UserService userService, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String register(UserDto userDto) {
        User user = userService.create(userDto);
        ConfirmationToken token = confirmationTokenService.create(user);
        return token.getToken();
    }

//    private

//    public User confirm() {
//
//    }

}
