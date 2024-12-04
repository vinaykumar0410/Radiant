package com.vinay.radiant.service;

import com.vinay.radiant.Exception.ChatNotFoundException;
import com.vinay.radiant.dto.MessageDto;
import com.vinay.radiant.dto.UserDto;

import java.util.List;

public interface MessageService {

    public MessageDto addMessage(UserDto userDto, Integer chatId, MessageDto messageDto) throws ChatNotFoundException;

    public List<MessageDto> findByChatId(Integer chatId) throws ChatNotFoundException;

}
