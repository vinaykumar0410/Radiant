package com.vinay.radiant.controller;

import com.vinay.radiant.Exception.ChatNotFoundException;
import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.ChatDto;
import com.vinay.radiant.dto.ChatRequest;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.service.ChatService;
import com.vinay.radiant.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ChatDto> createChat(
            @RequestHeader("Authorization") String jwt,
            @RequestBody ChatRequest chatRequest
    ) throws UserNotFoundException {
        UserDto reqUserDto = userService.findUserByJwtToken(jwt);
        UserDto userDto = userService.findUserById(chatRequest.getUserId());
        ChatDto chatDto = chatService.addChat(reqUserDto, userDto);
        return new ResponseEntity<>(chatDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ChatDto> getChatById(
            @RequestHeader("Authorization") String jwt
    ) throws ChatNotFoundException {
        UserDto userDto = userService.findUserByJwtToken(jwt);
        ChatDto chatDto = chatService.findChatById(userDto.getId());
        return new ResponseEntity<>(chatDto, HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<ChatDto>> getChatsByUserId(
            @PathVariable("userId") Integer userId
    ){
        return new ResponseEntity<>(
                chatService.findChatsByUserId(userId), HttpStatus.OK);
    }



}
