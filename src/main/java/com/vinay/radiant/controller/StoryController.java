package com.vinay.radiant.controller;

import com.vinay.radiant.dto.StoryDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.service.StoryService;
import com.vinay.radiant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<StoryDto> createStory(
            @RequestBody StoryDto storyDto,
            @RequestHeader("Authorization") String jwt
    ){
        UserDto userDto = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(storyService.addStory(storyDto,userDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StoryDto>> getUserStories(
            @PathVariable("userId") Integer userId
    ){
        return new ResponseEntity<>(storyService.findByUserId(userId),HttpStatus.OK);
    }


}
