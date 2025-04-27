package ru.sstu.socialnetworkbackend.services;

import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.entities.ConfirmationToken;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.repositories.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;

    public ConfirmationTokenService(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    public ConfirmationToken create(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        return repository.save(confirmationToken);
    }
//
//    public ConfirmationToken

}
