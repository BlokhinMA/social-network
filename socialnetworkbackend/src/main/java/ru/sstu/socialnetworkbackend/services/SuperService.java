package ru.sstu.socialnetworkbackend.services;

import org.springframework.security.access.AccessDeniedException;
import ru.sstu.socialnetworkbackend.entities.User;

public class SuperService {

    private static final String EXCEPTION_MSG = "У Вас недостаточно прав на выполнение данной операции";

    protected void checkRights(User currentUser, User user) {
        if (!currentUser.equals(user))
            throw new AccessDeniedException(EXCEPTION_MSG);
    }

    protected void checkRights(boolean isMember, User currentUser, User creator) {
        if (!isMember && !currentUser.equals(creator))
            throw new AccessDeniedException(EXCEPTION_MSG);
    }

    protected void checkRights(User currentUser, User author, User creator) {
        if (!currentUser.equals(author) && !currentUser.equals(creator))
            throw new AccessDeniedException(EXCEPTION_MSG);
    }

}
