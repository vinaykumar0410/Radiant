package com.vinay.radiant.service;

import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.UserDto;

import java.util.List;

public interface UserService {

    public UserDto findUserById(Integer userId) throws UserNotFoundException;

    public UserDto findUserByEmail(String email);

    public List<UserDto> getAllUsers();

    public UserDto followUser(Integer reqUserId, Integer userId2) throws UserNotFoundException;

    public UserDto updateUserById(Integer userId, UserDto userDto) throws UserNotFoundException;

    public List<UserDto> searchUser(String query);

    public UserDto findUserByJwtToken(String jwt);

}
