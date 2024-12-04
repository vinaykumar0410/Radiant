package com.vinay.radiant.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String caption;
    private String image;
    private String video;
    @ManyToOne
    private User user;
    private LocalDateTime createdAt;
    @OneToMany
    private List<User> liked = new ArrayList<>();
    @OneToMany
    private List<Comment> comments = new ArrayList<>();

}
