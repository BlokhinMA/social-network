package ru.sstu.socialnetworkbackend.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.entities.ConfirmationToken;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.exceptions.ExpiredTokenException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetworkbackend.exceptions.TokenAlreadyConfirmed;
import ru.sstu.socialnetworkbackend.repositories.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;
    private final UserService userService;

    public ConfirmationTokenService(ConfirmationTokenRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
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

    @Transactional
    public ConfirmationToken update(String token) {
        ConfirmationToken confirmationToken = repository.findByToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Токен не существует"));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmed();
        }
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            repository.deleteById(confirmationToken.getId());
            userService.delete(confirmationToken.getUser().getId());
            throw new ExpiredTokenException();
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationToken = repository.save(confirmationToken);
        return confirmationToken;
    }

}
