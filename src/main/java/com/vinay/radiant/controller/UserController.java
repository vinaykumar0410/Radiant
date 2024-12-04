package com.vinay.radiant.controller;

import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.User;
import com.vinay.radiant.security.JwtConstant;
import com.vinay.radiant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable("id") Integer userId
    ) throws UserNotFoundException {
        return new ResponseEntity<>(userService.findUserById(userId),HttpStatus.OK);
    }

    @GetMapping("/api/users/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(
            @PathVariable("email") String email
    ){
        return new ResponseEntity<>(userService.findUserByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserDto>> getUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @PutMapping("/api/users")
    public ResponseEntity<UserDto> updateUser(
            @RequestHeader("Authorization") String jwt,
            @RequestBody UserDto userDto
    ) throws UserNotFoundException {
        UserDto reqUserDto = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(userService.updateUserById(reqUserDto.getId(),userDto),HttpStatus.OK);
    }

    @PutMapping("/api/users/follow/{userId}")
    public ResponseEntity<UserDto> followUser(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("userId") Integer userId
    ) throws UserNotFoundException {
            UserDto reqUserDto = userService.findUserByJwtToken(jwt);
            return new ResponseEntity<>(userService.followUser(reqUserDto.getId(), userId),HttpStatus.OK);
    }

    @GetMapping("/api/users/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam("query") String query){
        List<UserDto> users = userService.searchUser(query);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDto> getUserFromJwtToken(@RequestHeader("Authorization") String jwt){
        UserDto userDto = userService.findUserByJwtToken(jwt);
        userDto.setPassword(null);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

}
