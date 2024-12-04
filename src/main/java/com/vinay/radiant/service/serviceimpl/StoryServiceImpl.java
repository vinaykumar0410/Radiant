package com.vinay.radiant.service.serviceimpl;

import com.vinay.radiant.dto.StoryDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.Story;
import com.vinay.radiant.model.User;
import com.vinay.radiant.repository.StoryRepository;
import com.vinay.radiant.service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StoryDto addStory(StoryDto storyDto, UserDto userDto) {
        Story story = modelMapper.map(storyDto, Story.class);
        User user = modelMapper.map(userDto, User.class);
        story.setUser(user);
        story.setTimeStamp(LocalDateTime.now());
        Story savedStory = storyRepository.save(story);
        return modelMapper.map(savedStory, StoryDto.class);
    }

    @Override
    public List<StoryDto> findByUserId(Integer userId) {
        List<Story> stories = storyRepository.findByUserId(userId);
        return stories.stream()
                .map(story -> modelMapper.map(story, StoryDto.class))
                .collect(Collectors.toList());
    }
}
