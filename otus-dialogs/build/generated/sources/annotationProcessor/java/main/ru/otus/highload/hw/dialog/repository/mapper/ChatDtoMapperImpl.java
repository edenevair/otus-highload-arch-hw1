package ru.otus.highload.hw.dialog.repository.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.otus.highload.hw.dialog.model.Chat;
import ru.otus.highload.hw.dialog.repository.model.ChatDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-02T12:43:21+0300",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.1.jar, environment: Java 11.0.8 (OpenLogic)"
)
@Component
public class ChatDtoMapperImpl implements ChatDtoMapper {

    @Override
    public ChatDto entityToDto(Chat chat) {
        if ( chat == null ) {
            return null;
        }

        ChatDto chatDto = new ChatDto();

        chatDto.setId( chat.getId() );
        chatDto.setUser1Id( chat.getUser1Id() );
        chatDto.setUser2Id( chat.getUser2Id() );
        chatDto.setUpdatedAt( chat.getUpdatedAt() );

        return chatDto;
    }

    @Override
    public List<ChatDto> entityListToDto(List<Chat> chats) {
        if ( chats == null ) {
            return null;
        }

        List<ChatDto> list = new ArrayList<ChatDto>( chats.size() );
        for ( Chat chat : chats ) {
            list.add( entityToDto( chat ) );
        }

        return list;
    }
}
