package com.vinay.radiant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String image;
    @ManyToMany
    private List<User> users = new ArrayList<>();
    private LocalDateTime timeStamp;
    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

}
