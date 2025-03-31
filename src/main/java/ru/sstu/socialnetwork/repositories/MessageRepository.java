package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {



}
