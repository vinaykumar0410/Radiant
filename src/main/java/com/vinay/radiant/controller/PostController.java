package com.vinay.radiant.controller;

import com.vinay.radiant.Exception.PostNotFoundException;
import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.PostDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.service.PostService;
import com.vinay.radiant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<PostDto> addPost(
            @RequestBody PostDto postDto,
            @RequestHeader("Authorization") String jwt
    ) throws UserNotFoundException {
        UserDto reqUserDto = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(
                postService.createPost(postDto, reqUserDto.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/update/{postId}/user/{userId}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable("postId") Integer postId,
            @PathVariable("userId") Integer userId,
            @RequestBody PostDto postDto
    ) throws UserNotFoundException, PostNotFoundException {
        return new ResponseEntity<>(
                postService.updatePost(postId,userId,postDto),HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDto> removePost(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("postId") Integer postId
    ) throws UserNotFoundException, PostNotFoundException {
        UserDto reqUserDto = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(
                postService.deletePost(postId, reqUserDto.getId()), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(
            @PathVariable("postId") Integer postId
    ) throws PostNotFoundException {
        return new ResponseEntity<>(
                postService.findPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getUserPosts(
            @PathVariable("userId") Integer userId
    ){
        return new ResponseEntity<>(
                postService.findPostByUserId(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost(){
        return new ResponseEntity<>(
                postService.findAllPosts(), HttpStatus.OK);
    }

    @PutMapping("/save/{postId}")
    public ResponseEntity<PostDto> savePost(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("postId") Integer postId
    ) throws UserNotFoundException, PostNotFoundException {
        UserDto reqUserDto = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(
                postService.savePost(postId, reqUserDto.getId()), HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<PostDto> likePost(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("postId") Integer postId
    ) throws UserNotFoundException, PostNotFoundException {
        UserDto reqUserDto = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(
                postService.likePost(postId, reqUserDto.getId()),HttpStatus.OK);
    }

}
