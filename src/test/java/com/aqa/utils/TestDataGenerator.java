package com.aqa.utils;

import com.aqa.api.models.UserRegisterDto;
import com.github.javafaker.Faker;

public class TestDataGenerator {
    
    private static final Faker faker = new Faker();
    
    public static UserRegisterDto generateRandomUser() {
        String username = faker.name().username(); 
        String email = faker.internet().emailAddress(); 
        String password = faker.internet().password(8, 16, true, true);

        UserRegisterDto.User userFields = new UserRegisterDto.User(username, email, password);
        return new UserRegisterDto(userFields);
    } 
}
