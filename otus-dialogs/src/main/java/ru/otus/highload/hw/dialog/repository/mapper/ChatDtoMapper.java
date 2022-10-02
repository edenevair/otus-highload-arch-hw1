package ru.otus.highload.hw.dialog.repository.mapper;

import org.mapstruct.Mapper;
import ru.otus.highload.hw.dialog.model.Chat;
import ru.otus.highload.hw.dialog.repository.model.ChatDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatDtoMapper {

    ChatDto entityToDto(Chat chat);

    List<ChatDto> entityListToDto(List<Chat> chats);

    default Chat dtoToEntity(ChatDto dto) {
        return Chat.builder()
                .user1Id(dto.getUser1Id())
                .user2Id(dto.getUser2Id())
                .build();
    }
}
