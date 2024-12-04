package com.vinay.radiant.service.serviceimpl;

import com.vinay.radiant.Exception.ChatNotFoundException;
import com.vinay.radiant.dto.ChatDto;
import com.vinay.radiant.dto.MessageDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.Chat;
import com.vinay.radiant.model.Message;
import com.vinay.radiant.model.User;
import com.vinay.radiant.repository.ChatRepository;
import com.vinay.radiant.repository.MessageRepository;
import com.vinay.radiant.service.ChatService;
import com.vinay.radiant.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageDto addMessage(UserDto userDto, Integer chatId, MessageDto messageDto) throws ChatNotFoundException {
        ChatDto chatDto = chatService.findChatById(chatId);
        Chat chat = modelMapper.map(chatDto, Chat.class);
        User user = modelMapper.map(userDto, User.class);
        Message message = modelMapper.map(messageDto, Message.class);
        message.setChat(chat);
        message.setUser(user);
        message.setTimeStamp(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
        return modelMapper.map(savedMessage, MessageDto.class);
    }

    @Override
    public List<MessageDto> findByChatId(Integer chatId) throws ChatNotFoundException {
        ChatDto chatDto = chatService.findChatById(chatId);
        if(chatDto == null){
            throw new ChatNotFoundException("Chat Not Found With ChatId : " + chatId);
        }
        List<Message> messages = messageRepository.findByChatId(chatId);
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
    }
}
