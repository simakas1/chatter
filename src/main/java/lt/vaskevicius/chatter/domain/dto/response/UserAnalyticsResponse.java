package lt.vaskevicius.chatter.domain.dto.response;

import lombok.Data;
import lt.vaskevicius.chatter.domain.entity.User;

import java.time.LocalDateTime;

@Data
public class UserAnalyticsResponse {

    private User user;

    private int sentMessages;

    private int receivedMessages;

    private LocalDateTime firstReceivedMessageCreatedAt;

    private LocalDateTime firstSentMessageCreatedAt;

    private LocalDateTime lastReceivedMessageCreatedAt;

    private LocalDateTime lastSentMessageCreatedAt;

    private Double averageSentMessageContentLength;

    private Double averageReceivedMessageContentLength;

    private String lastSentMessageContent;

    private String lastReceivedMessageContent;

}
