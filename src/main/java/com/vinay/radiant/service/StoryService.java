package com.vinay.radiant.service;

import com.vinay.radiant.dto.StoryDto;
import com.vinay.radiant.dto.UserDto;

import java.util.List;

public interface StoryService {

    public StoryDto addStory(StoryDto storyDto, UserDto userDto);

    public List<StoryDto> findByUserId(Integer userId);
}
