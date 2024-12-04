package com.vinay.radiant.controller;

import com.vinay.radiant.Exception.ChatNotFoundException;
import com.vinay.radiant.dto.MessageDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.service.MessageService;
import com.vinay.radiant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<MessageDto> createMessage(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("chatId") Integer chatId,
            @RequestBody MessageDto messageDto
    ) throws ChatNotFoundException {
        UserDto reqUser = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(
                messageService.addMessage(reqUser, chatId, messageDto),
                HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageDto>> getMessages(
        @PathVariable("chatId") Integer chatId
    ) throws ChatNotFoundException {
        return new ResponseEntity<>(
                messageService.findByChatId(chatId), HttpStatus.OK);
    }

}
