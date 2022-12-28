package lt.vaskevicius.chatter.service;

import lt.vaskevicius.chatter.domain.dto.response.GlobalAnalyticsResponse;
import lt.vaskevicius.chatter.domain.dto.response.UserAnalyticsResponse;
import lt.vaskevicius.chatter.domain.entity.Message;
import lt.vaskevicius.chatter.domain.entity.User;
import lt.vaskevicius.chatter.domain.exception.ChatterException;
import lt.vaskevicius.chatter.domain.exception.ChatterExceptionCode;
import lt.vaskevicius.chatter.repository.MessageRepository;
import lt.vaskevicius.chatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public UserAnalyticsResponse getUserAnalytics(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ChatterException(ChatterExceptionCode.CHAT4, "User not found"));

        Double averageSentContentLength = messageRepository.findAverageSentContentLength(user.getId());
        Double averageReceivedContentLength = messageRepository.findAverageReceivedContentLength(user.getId());

        Integer sentMessagesCount = messageRepository.findSentMessagesCount(user.getId());
        Integer receivedMessagesCount = messageRepository.findReceivedMessagesCount(user.getId());

        Message lastSentMessage = messageRepository.findLastSentMessage(user.getId()).orElse(null);
        Message lastReceivedMessage = messageRepository.findLastReceivedMessage(user.getId()).orElse(null);

        Message firstSentMessage = messageRepository.findFirstSentMessage(user.getId()).orElse(null);
        Message firstReceivedMessage = messageRepository.findFirstReceivedMessage(user.getId()).orElse(null);

        UserAnalyticsResponse userAnalyticsResponse = new UserAnalyticsResponse();

        userAnalyticsResponse.setUser(user);

        userAnalyticsResponse.setAverageSentMessageContentLength(averageSentContentLength);
        userAnalyticsResponse.setAverageReceivedMessageContentLength(averageReceivedContentLength);

        userAnalyticsResponse.setSentMessages(sentMessagesCount);
        userAnalyticsResponse.setReceivedMessages(receivedMessagesCount);

        if (lastSentMessage != null) {
            userAnalyticsResponse.setLastSentMessageContent(lastSentMessage.getContent());
            userAnalyticsResponse.setLastSentMessageCreatedAt(lastSentMessage.getCreatedAt());
        }

        if (lastReceivedMessage != null) {
            userAnalyticsResponse.setLastReceivedMessageContent(lastReceivedMessage.getContent());
            userAnalyticsResponse.setLastReceivedMessageCreatedAt(lastReceivedMessage.getCreatedAt());
        }

        if (firstSentMessage != null) {
            userAnalyticsResponse.setFirstSentMessageCreatedAt(firstSentMessage.getCreatedAt());
        }

        if (firstReceivedMessage != null) {
            userAnalyticsResponse.setFirstReceivedMessageCreatedAt(firstReceivedMessage.getCreatedAt());
        }

        return userAnalyticsResponse;
    }

    public GlobalAnalyticsResponse getGlobalAnalytics() {
        Double averageContentLength = messageRepository.findAverageContentLength();
        Integer messageCount = messageRepository.findMessagesCount();

        Message firstMessage = messageRepository.findFirstMessage().orElse(null);
        Message lastMessage = messageRepository.findLastMessage().orElse(null);

        GlobalAnalyticsResponse globalAnalyticsResponse = new GlobalAnalyticsResponse();

        globalAnalyticsResponse.setMessageCount(messageCount);
        globalAnalyticsResponse.setAverageContentLength(averageContentLength);

        if (firstMessage != null) {
            globalAnalyticsResponse.setFirstMessageCreatedAt(firstMessage.getCreatedAt());
            globalAnalyticsResponse.setFirstMessageContent(firstMessage.getContent());
        }

        if (lastMessage != null) {
            globalAnalyticsResponse.setLastMessageCreatedAt(lastMessage.getCreatedAt());
            globalAnalyticsResponse.setLastMessageContent(lastMessage.getContent());
        }

        return globalAnalyticsResponse;
    }

}
