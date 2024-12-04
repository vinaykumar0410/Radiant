package com.vinay.radiant.service.serviceimpl;

import com.vinay.radiant.Exception.ChatNotFoundException;
import com.vinay.radiant.dto.ChatDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.Chat;
import com.vinay.radiant.model.User;
import com.vinay.radiant.repository.ChatRepository;
import com.vinay.radiant.service.ChatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ChatDto addChat(UserDto reqUserDto, UserDto targetUserDto) {
        User reqUser = modelMapper.map(reqUserDto, User.class);
        User targetUser = modelMapper.map(targetUserDto, User.class);
        Chat existingchat = chatRepository.findChatByUsersId(reqUser, targetUser);
        if(existingchat!=null){
            return modelMapper.map(existingchat, ChatDto.class);
        }
        Chat chat = new Chat();
        chat.getUsers().add(targetUser);
        chat.getUsers().add(reqUser);
        chat.setTimeStamp(LocalDateTime.now());
        Chat savedChat = chatRepository.save(chat);
        return modelMapper.map(savedChat, ChatDto.class);
    }

    @Override
    public ChatDto findChatById(Integer chatId) throws ChatNotFoundException {
        Chat chat = chatRepository.findById(chatId).orElseThrow(
                ()-> new ChatNotFoundException("Chat Not Found With ChatId : " + chatId)
        );
        return modelMapper.map(chat, ChatDto.class);
    }

    @Override
    public List<ChatDto> findChatsByUserId(Integer userId) {
        List<Chat> chats = chatRepository.findByUsersId(userId);
        return chats.stream()
                .map(chat -> modelMapper.map(chat, ChatDto.class))
                .collect(Collectors.toList());
    }
}
