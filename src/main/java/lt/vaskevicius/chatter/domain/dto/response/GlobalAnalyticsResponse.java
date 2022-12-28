package lt.vaskevicius.chatter.domain.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GlobalAnalyticsResponse {
    private int messageCount;

    private Double averageContentLength;

    private LocalDateTime firstMessageCreatedAt;

    private String firstMessageContent;

    private LocalDateTime lastMessageCreatedAt;

    private String lastMessageContent;
}
