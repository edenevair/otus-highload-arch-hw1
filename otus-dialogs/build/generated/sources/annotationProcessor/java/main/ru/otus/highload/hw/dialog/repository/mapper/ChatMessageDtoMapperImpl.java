package ru.otus.highload.hw.dialog.repository.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.otus.highload.hw.dialog.model.ChatMessage;
import ru.otus.highload.hw.dialog.repository.model.ChatMessageDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-02T12:47:01+0300",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.1.jar, environment: Java 11.0.8 (OpenLogic)"
)
@Component
public class ChatMessageDtoMapperImpl implements ChatMessageDtoMapper {

    @Override
    public ChatMessageDto entityToDto(ChatMessage message) {
        if ( message == null ) {
            return null;
        }

        ChatMessageDto chatMessageDto = new ChatMessageDto();

        chatMessageDto.setId( message.getId() );
        chatMessageDto.setChatId( message.getChatId() );
        chatMessageDto.setUserId( message.getUserId() );
        chatMessageDto.setCreatedAt( message.getCreatedAt() );
        chatMessageDto.setMessage( message.getMessage() );

        return chatMessageDto;
    }

    @Override
    public List<ChatMessageDto> entityListToDto(List<ChatMessage> messages) {
        if ( messages == null ) {
            return null;
        }

        List<ChatMessageDto> list = new ArrayList<ChatMessageDto>( messages.size() );
        for ( ChatMessage chatMessage : messages ) {
            list.add( entityToDto( chatMessage ) );
        }

        return list;
    }
}
