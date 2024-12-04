package com.vinay.radiant.service.serviceimpl;

import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.ReelDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.Reel;
import com.vinay.radiant.model.User;
import com.vinay.radiant.repository.ReelRepository;
import com.vinay.radiant.service.ReelService;
import com.vinay.radiant.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReelServiceImpl implements ReelService {

    @Autowired
    private ReelRepository reelRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReelDto addReel(ReelDto reelDto, UserDto userDto) {
        Reel reel = modelMapper.map(reelDto, Reel.class);
        User user = modelMapper.map(userDto, User.class);
        reel.setUser(user);
        Reel savedReel = reelRepository.save(reel);
        return modelMapper.map(savedReel, ReelDto.class);
    }

    @Override
    public List<ReelDto> findAllReels() {
        List<Reel> reels = reelRepository.findAll();
        return reels.stream()
                .map(reel->modelMapper.map(reel, ReelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReelDto> findUserReels(Integer userId) throws UserNotFoundException {
        userService.findUserById(userId);
        List<Reel> reels = reelRepository.findByUserId(userId);
        return reels.stream()
                .map(reel->modelMapper.map(reel, ReelDto.class))
                .collect(Collectors.toList());
    }
}
