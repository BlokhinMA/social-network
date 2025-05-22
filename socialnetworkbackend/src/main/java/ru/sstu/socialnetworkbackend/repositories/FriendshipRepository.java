package ru.sstu.socialnetworkbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetworkbackend.entities.Friendship;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query(value = "SELECT * FROM users WHERE CONCAT(first_name, ' ', last_name) ILIKE %?1% AND id != ?2 AND " +
            "id NOT IN (SELECT second_user_id FROM friendships WHERE first_user_id = ?2 UNION " +
            "SELECT first_user_id FROM friendships WHERE second_user_id = ?2)",
            nativeQuery = true)
    List<User> findAllLikeFirstNameOrLastName(String keyword, Long userId);

    @Query(value = "SELECT u.* FROM users u JOIN friendships ON u.id = first_user_id " +
            "WHERE second_user_id = ?1 AND accepted = false", nativeQuery = true)
    List<User> findIncomingRequestsByUserId(Long userId);

    @Query(value = "SELECT u.* FROM users u JOIN friendships ON u.id = second_user_id " +
            "WHERE first_user_id = ?1 AND accepted = false", nativeQuery = true)
    List<User> findOutgoingRequestsByUserId(Long id);

    @Query(value = "SELECT * FROM friendships WHERE first_user_id = ?1 AND second_user_id = ?2 AND accepted = false",
            nativeQuery = true)
    Optional<Friendship> findNotAcceptedByFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId);

    @Query(value = "SELECT u.* FROM users u JOIN friendships ON u.id = second_user_id WHERE first_user_id = ?1 " +
            "AND accepted = true UNION SELECT u.* FROM users u JOIN friendships ON u.id = first_user_id " +
            "WHERE second_user_id = ?1 AND accepted = true", nativeQuery = true)
    List<User> findAllAcceptedByUserId(Long id);

    @Query(value = "SELECT * FROM friendships WHERE first_user_id IN (?1, ?2) AND second_user_id " +
            "IN (?1, ?2) AND accepted = true", nativeQuery = true)
    Optional<Friendship> findAcceptedByFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserid);

}
