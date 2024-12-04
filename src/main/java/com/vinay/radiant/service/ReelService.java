package com.vinay.radiant.service;

import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.ReelDto;
import com.vinay.radiant.dto.UserDto;

import java.util.List;

public interface ReelService {

    public ReelDto addReel(ReelDto reelDto, UserDto userDto);

    public List<ReelDto> findAllReels();

    public List<ReelDto> findUserReels(Integer userId) throws UserNotFoundException;

}
