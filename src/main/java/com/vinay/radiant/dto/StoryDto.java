package com.vinay.radiant.dto;

import com.vinay.radiant.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryDto {

    private Integer id;
    private String caption;
    private String image;
    private User user;
    private LocalDateTime timeStamp;

}
