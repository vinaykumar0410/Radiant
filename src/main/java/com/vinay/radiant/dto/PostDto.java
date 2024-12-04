package com.vinay.radiant.dto;

import com.vinay.radiant.model.Comment;
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
public class PostDto {

    private Integer id;
    private String caption;
    private String image;
    private String video;
    private User user;
    private LocalDateTime createdAt;
    private List<User> liked;
    private List<Comment> comments = new ArrayList<>();
}
