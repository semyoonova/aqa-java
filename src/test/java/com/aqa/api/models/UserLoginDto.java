package com.aqa.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    private User user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String email;
        private String password;
    }  
}
