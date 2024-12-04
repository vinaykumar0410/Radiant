package com.vinay.radiant.dto;

import com.vinay.radiant.model.Chat;
import com.vinay.radiant.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Integer id;
    private String content;
    private String image;
    private User user;
    private Chat chat;
    private LocalDateTime timeStamp;

}
