package com.vinay.radiant.service.serviceimpl;

import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.UserDto;


import com.vinay.radiant.model.User;
import com.vinay.radiant.repository.UserRepository;
import com.vinay.radiant.security.JwtProvider;
import com.vinay.radiant.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto findUserById(Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found With UserId : " + userId)
        );
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
    }

    @Override
    public UserDto followUser(Integer reqUserId, Integer userId2) throws UserNotFoundException {
        User user1 = userRepository.findById(reqUserId).orElseThrow(
                () -> new UserNotFoundException("User Not Found With UserId : " + reqUserId)
        );
        User user2 = userRepository.findById(userId2).orElseThrow(
                () -> new UserNotFoundException("User Not Found With UserId : " + userId2)
        );
        if(Objects.equals(user1.getId(), userId2)){
            throw new BadCredentialsException("You Can't follow your account");
        }
        if(!user1.getFollowing().contains(user2.getId())){
            user1.getFollowing().add(user2.getId());
            user2.getFollowers().add(user1.getId());
            userRepository.save(user1);
            userRepository.save(user2);
        }
        return modelMapper.map(user1, UserDto.class);
    }

    @Override
    public UserDto updateUserById(Integer userId, UserDto userDto) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found With UserId : " + userId)
        );
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return users.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserByJwtToken(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

}
