package com.vinay.radiant.dto;

import lombok.Data;

@Data
public class TokenResponse {

    private String token;
    private String message;

    public TokenResponse(String token,String message){
        this.token = token;
        this.message = message;
    }
}
