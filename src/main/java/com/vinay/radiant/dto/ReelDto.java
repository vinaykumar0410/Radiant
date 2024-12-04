package com.vinay.radiant.dto;

import com.vinay.radiant.model.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReelDto {

    private Integer id;
    private String title;
    private String video;
    private User user;

}
