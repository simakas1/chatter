package lt.vaskevicius.chatter.repository;

import lt.vaskevicius.chatter.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT * FROM MESSAGE WHERE RECEIVER_USER_ID = :receiverId AND CREATED_AT BETWEEN :from AND :to LIMIT :limit", nativeQuery = true)
    List<Message> findAllMessages(@Param("receiverId") Integer receiverId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("limit") Integer limit);


    //Average content length
    @Query(value = "SELECT AVG(length(content)) FROM MESSAGE", nativeQuery = true)
    Double findAverageContentLength();

    @Query(value = "SELECT AVG(length(content)) FROM MESSAGE WHERE SENDER_USER_ID = :senderId", nativeQuery = true)
    Double findAverageSentContentLength(@Param("senderId") int senderId);

    @Query(value = "SELECT AVG(length(content)) FROM MESSAGE WHERE RECEIVER_USER_ID = :receiverId", nativeQuery = true)
    Double findAverageReceivedContentLength(@Param("receiverId") int receiverId);

    //Message count
    @Query(value = "SELECT COUNT(*) FROM MESSAGE", nativeQuery = true)
    Integer findMessagesCount();

    @Query(value = "SELECT COUNT(*) FROM MESSAGE WHERE SENDER_USER_ID = :senderId", nativeQuery = true)
    Integer findSentMessagesCount(@Param("senderId") int senderId);

    @Query(value = "SELECT COUNT(*) FROM MESSAGE WHERE RECEIVER_USER_ID = :receiverId", nativeQuery = true)
    Integer findReceivedMessagesCount(@Param("receiverId") int receiverId);


    //Last/first message
    @Query(value = "SELECT * FROM MESSAGE WHERE SENDER_USER_ID = :senderId ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    Optional<Message> findLastSentMessage(@Param("senderId") int senderId);

    @Query(value = "SELECT * FROM MESSAGE WHERE RECEIVER_USER_ID = :receiverId ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    Optional<Message> findLastReceivedMessage(@Param("receiverId") int receiverId);

    @Query(value = "SELECT * FROM MESSAGE WHERE SENDER_USER_ID = :senderId ORDER BY ID ASC LIMIT 1", nativeQuery = true)
    Optional<Message> findFirstSentMessage(@Param("senderId") int senderId);

    @Query(value = "SELECT * FROM MESSAGE WHERE RECEIVER_USER_ID = :receiverId ORDER BY ID ASC LIMIT 1", nativeQuery = true)
    Optional<Message> findFirstReceivedMessage(@Param("receiverId") int receiverId);

    @Query(value = "SELECT * FROM MESSAGE ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    Optional<Message> findLastMessage();

    @Query(value = "SELECT * FROM MESSAGE ORDER BY ID ASC LIMIT 1", nativeQuery = true)
    Optional<Message> findFirstMessage();
}
