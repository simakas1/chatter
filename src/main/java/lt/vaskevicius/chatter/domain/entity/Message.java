package lt.vaskevicius.chatter.domain.entity;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table
@Entity
public class Message {

    public enum MessageStatus {
        DRAFT,
        SENT,
        READ
    }

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "sender_user_id")
    private int senderId;

    @Column(name = "receiver_user_id")
    private int receiverId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.DRAFT;

}
