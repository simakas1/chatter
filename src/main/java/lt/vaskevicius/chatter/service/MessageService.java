package lt.vaskevicius.chatter.service;

import lt.vaskevicius.chatter.domain.dto.request.MessageCreateRequest;
import lt.vaskevicius.chatter.domain.entity.Message;
import lt.vaskevicius.chatter.domain.entity.User;
import lt.vaskevicius.chatter.domain.exception.ChatterException;
import lt.vaskevicius.chatter.domain.exception.ChatterExceptionCode;
import lt.vaskevicius.chatter.repository.MessageRepository;
import lt.vaskevicius.chatter.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessages(User receiver, String from, String to, Integer limit) {
        var fromLocalDate = DateUtil.parseLocalDate(from);
        var toLocalDate = DateUtil.parseLocalDate(to);

        validateFromToDates(fromLocalDate, toLocalDate);
        var messages = messageRepository.findAllMessages(
                receiver.getId(),
                fromLocalDate,
                toLocalDate,
                limit);

        messages.forEach(this::setMessageAsRead);

        return messages;
    }

    private void setMessageAsRead(Message message) {
        message.setStatus(Message.MessageStatus.READ);
        messageRepository.save(message);
    }


    public Message saveMessage(MessageCreateRequest messageCreateDto, User sender) {
        Message message = messageCreateDto.toEntity();
        message.setCreatedAt(LocalDateTime.now());
        message.setSenderId(sender.getId());
        message.setStatus(Message.MessageStatus.SENT);

        return messageRepository.save(message);
    }

    private void validateFromToDates(LocalDateTime from, LocalDateTime to) {
        if (to.isBefore(from)) {
            throw new ChatterException(ChatterExceptionCode.CHAT2, "To date cannot be before from date");
        }
    }

}
