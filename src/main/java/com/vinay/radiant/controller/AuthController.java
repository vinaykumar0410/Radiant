package com.vinay.radiant.controller;

import com.vinay.radiant.dto.LoginDto;
import com.vinay.radiant.dto.TokenResponse;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.User;
import com.vinay.radiant.repository.UserRepository;
import com.vinay.radiant.security.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) throws Exception {
        User isExist = userRepository.findByEmail(userDto.getEmail());
        if(isExist != null){
            throw new Exception("This email is already used with another account");
        }
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(modelMapper.map(savedUser, UserDto.class),HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginDto loginDto){
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid UserName");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Password Mismatch");
        }
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(email, password);
        String token = JwtProvider.generateToken(authentication);
        return new ResponseEntity<>(new TokenResponse(token,"Login Successful"), HttpStatus.CREATED);
    }


}
