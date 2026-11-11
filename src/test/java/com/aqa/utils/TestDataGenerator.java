package com.aqa.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aqa.api.models.CreateArticleDto;
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

    public static CreateArticleDto generateRandomArticle() {
        var tags = new ArrayList<>(List.of("#java", "#aqa", "#testing", "#faker"));
        Collections.shuffle(tags);
        Set<String> uniqueTags = Set.of(
            "#" + faker.lorem().word() + System.nanoTime(),
            "#" + faker.lorem().word() + System.nanoTime()
        );

        CreateArticleDto.Article articleFields = new CreateArticleDto.Article(
            faker.lorem().sentence(5),
            faker.lorem().sentence(10),
            faker.lorem().paragraph(10),
            uniqueTags
        );
        return new CreateArticleDto(articleFields);
    }
}
