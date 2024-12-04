package com.vinay.radiant.dto;

import com.vinay.radiant.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Integer id;
    private String content;
    private User user;
    private List<User> liked = new ArrayList<>();
    private LocalDateTime createdAt;

}
