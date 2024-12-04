package com.vinay.radiant.controller;

import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.ReelDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.User;
import com.vinay.radiant.service.ReelService;
import com.vinay.radiant.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reels")
public class ReelController {

    @Autowired
    private ReelService reelService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ReelDto> createReel(
            @RequestBody ReelDto reelDto,
            @RequestHeader("Authorization") String jwt
    ){
        UserDto userDto = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(
                reelService.addReel(reelDto, userDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReelDto>> getAllReels(){
        return new ResponseEntity<>(
                reelService.findAllReels(),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReelDto>> getUserReels(
            @PathVariable("userId") Integer userId
    ) throws UserNotFoundException {
        return new ResponseEntity<>(
                reelService.findUserReels(userId),HttpStatus.OK);
    }

}
