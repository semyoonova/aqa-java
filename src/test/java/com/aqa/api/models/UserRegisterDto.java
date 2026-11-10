package com.aqa.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    private User user;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String username;
        private String email;
        private String password;
    }
}
