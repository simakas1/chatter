package lt.vaskevicius.chatter.domain.dto.request;

import lombok.Data;
import lt.vaskevicius.chatter.domain.dto.base.ConvertibleToEntity;
import lt.vaskevicius.chatter.domain.entity.Message;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MessageCreateRequest implements ConvertibleToEntity<Message> {

    @NotBlank(message = "Receiver id is mandatory")
    private Integer receiverId;

    @NotBlank(message = "Content is mandatory")
    @Size(min = 4, max = 255, message = "Minimum content length: 4 characters, maximum: 255 characters")
    private String content;

    @Override
    public Message toEntity() {
        Message message = new Message();
        message.setContent(content);
        message.setReceiverId(receiverId);

        return message;
    }
}
