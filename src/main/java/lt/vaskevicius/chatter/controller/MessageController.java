package lt.vaskevicius.chatter.controller;

import lt.vaskevicius.chatter.domain.dto.request.MessageCreateRequest;
import lt.vaskevicius.chatter.domain.entity.Message;
import lt.vaskevicius.chatter.domain.entity.User;
import lt.vaskevicius.chatter.domain.exception.ChatterException;
import lt.vaskevicius.chatter.domain.exception.ChatterExceptionCode;
import lt.vaskevicius.chatter.service.MessageService;
import lt.vaskevicius.chatter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("send")
    public Message sendMessage(@RequestBody MessageCreateRequest messageCreateDto, HttpServletRequest request) {
        User sender = userService.getUser(request);
        return messageService.saveMessage(messageCreateDto, sender);
    }

    @GetMapping
    public List<Message> getMessages(@RequestParam Optional<String> from, @RequestParam Optional<String> to, @RequestParam Optional<Integer> limit, HttpServletRequest request) {
        User receiver = userService.getUser(request);
        String dateFrom = from.orElseThrow(() -> new ChatterException(ChatterExceptionCode.CHAT2, "Parameter 'from' is mandatory"));
        String dateTo = to.orElseThrow(() -> new ChatterException(ChatterExceptionCode.CHAT2, "Parameter 'to' is mandatory"));
        return messageService.getMessages(receiver, dateFrom, dateTo, limit.orElse(10));
    }
}
