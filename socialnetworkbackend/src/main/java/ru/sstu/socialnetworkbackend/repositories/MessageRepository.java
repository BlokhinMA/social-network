package ru.sstu.socialnetworkbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetworkbackend.entities.Message;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT u.* FROM users u JOIN messages ON u.id = to_user_id WHERE from_user_id = ?1 " +
            "UNION SELECT u.* FROM users u JOIN messages ON u.id = from_user_id " +
            "WHERE to_user_id = ?1", nativeQuery = true)
    List<User> findAllCompanionsByUserId(Long id);

    @Query(value = "SELECT * FROM messages WHERE from_user_id IN (?1, ?2) AND to_user_id IN (?1, ?2) " +
            "ORDER BY writing_time_stamp LIMIT 1", nativeQuery = true)
    Message findLastByUserId(Long user1, Long user2);

    @Query(value = "SELECT * FROM messages WHERE from_user_id IN (?1, ?2) AND to_user_id IN (?1, ?2) " +
            "ORDER BY writing_time_stamp", nativeQuery = true)
    List<Message> findAllByUserId(Long user1, Long user2);

}
