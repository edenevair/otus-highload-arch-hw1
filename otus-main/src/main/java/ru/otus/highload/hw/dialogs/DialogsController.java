package ru.otus.highload.hw.dialogs;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.highload.hw.dialog.model.Chat;
import ru.otus.highload.hw.dialog.model.ChatMessage;
import ru.otus.highload.hw.dialog.repository.ChatMessageRepository;
import ru.otus.highload.hw.dialog.repository.ChatRepository;
import ru.otus.highload.hw.dialog.repository.mapper.ChatDtoMapper;
import ru.otus.highload.hw.dialog.repository.mapper.ChatMessageDtoMapper;
import ru.otus.highload.hw.dialog.repository.model.ChatDto;
import ru.otus.highload.hw.dialog.repository.model.ChatMessageDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dialogs")
@RequiredArgsConstructor
public class DialogsController {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository messageRepository;
    private final ChatDtoMapper chatDtoMapper;
    private final ChatMessageDtoMapper  chatMessageDtoMapper;

    @PostMapping("/")
    public ResponseEntity<Chat> createChat(@RequestBody ChatDto chat) {
        Chat createdChat = chatRepository.save(chatDtoMapper.dtoToEntity(chat));
        return ResponseEntity.ok(createdChat);
    }

    @GetMapping("/")
    public ResponseEntity<List<ChatDto>> findAllChatsForUser(@RequestParam(name = "userId") Long userId) {
        List<Chat> usersChats = chatRepository.findChatsForUser(userId);

        if (CollectionUtils.isEmpty(usersChats)) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<ChatDto> usersDtoChats = chatDtoMapper.entityListToDto(usersChats);
        return ResponseEntity.ok(usersDtoChats);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDto> findChatById(@PathVariable("chatId") String chatId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chatDtoMapper.entityToDto(chat.get()));
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<ChatMessage> createMessage(@PathVariable("chatId") String chatId, @RequestBody ChatMessageDto message) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ChatMessage newMessage = chatMessageDtoMapper.dtoToNewEntity(message, chatId);

        newMessage = messageRepository.save(newMessage);
        return ResponseEntity.ok(newMessage);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<ChatMessageDto>> getChatMessages(@PathVariable("chatId") String chatId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ChatMessage> messages = messageRepository.findMessagesByChat(chatId);
        return ResponseEntity.ok(chatMessageDtoMapper.entityListToDto(messages));
    }
}
