package com.vinay.radiant.dto;

import com.vinay.radiant.model.User;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    private Integer id;
    private String name;
    private String image;
    private List<User> users = new ArrayList<>();
    private LocalDateTime timeStamp;

}
