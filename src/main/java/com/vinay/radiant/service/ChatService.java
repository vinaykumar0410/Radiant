package com.vinay.radiant.service;

import com.vinay.radiant.Exception.ChatNotFoundException;
import com.vinay.radiant.dto.ChatDto;
import com.vinay.radiant.dto.UserDto;

import java.util.List;

public interface ChatService {

    public ChatDto addChat(UserDto reqUser, UserDto targetUser);

    public ChatDto findChatById(Integer chatId) throws ChatNotFoundException;

    public List<ChatDto> findChatsByUserId(Integer userId);

}
