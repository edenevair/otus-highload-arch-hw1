package ru.otus.highload.hw.dialog.repository.mapper;

import org.mapstruct.Mapper;
import ru.otus.highload.hw.dialog.model.Chat;
import ru.otus.highload.hw.dialog.model.ChatMessage;
import ru.otus.highload.hw.dialog.repository.model.ChatDto;
import ru.otus.highload.hw.dialog.repository.model.ChatMessageDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageDtoMapper {

    ChatMessageDto entityToDto(ChatMessage message);

    List<ChatMessageDto> entityListToDto(List<ChatMessage> messages);

    default ChatMessage dtoToNewEntity(ChatMessageDto dto, String chatId) {
        return ChatMessage.builder()
                .userId(dto.getUserId())
                .message(dto.getMessage())
                .chatId(chatId)
                .build();
    }
}
