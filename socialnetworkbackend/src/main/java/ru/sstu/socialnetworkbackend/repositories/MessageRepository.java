package ru.sstu.socialnetworkbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetworkbackend.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {



}
