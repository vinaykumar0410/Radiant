package com.vinay.radiant.controller;

import com.vinay.radiant.Exception.CommentNotFoundException;
import com.vinay.radiant.Exception.PostNotFoundException;
import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.CommentDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.service.CommentService;
import com.vinay.radiant.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    public CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> addComment(
            @RequestBody CommentDto commentDto,
            @RequestHeader("Authorization") String jwt,
            @PathVariable("postId") Integer postId
    ) throws UserNotFoundException, PostNotFoundException {
        UserDto userDto = userService.findUserByJwtToken(jwt);
        CommentDto createdComment = commentService.createComment(commentDto, userDto.getId(), postId);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<CommentDto> likeComment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("commentId") Integer commentId
    ) throws UserNotFoundException, CommentNotFoundException {
        UserDto userDto = userService.findUserByJwtToken(jwt);
        CommentDto likedComment = commentService.likeComment(commentId, userDto.getId());

        return new ResponseEntity<>(likedComment, HttpStatus.OK);
    }


}
