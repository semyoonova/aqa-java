package com.aqa.utils;
import java.util.Set;

import com.aqa.api.models.CreateArticleDto;
import com.aqa.api.models.UserRegisterDto;
import com.github.javafaker.Faker;

import io.qameta.allure.Step;

public class TestDataGenerator {
    
    private static final Faker faker = new Faker();
    
    @Step("Генерируем рандомного пользователя")
    public static UserRegisterDto generateRandomUser() {
        String username = faker.name().username(); 
        String email = faker.internet().emailAddress(); 
        String password = faker.internet().password(8, 16, true, true);

        UserRegisterDto.User userFields = new UserRegisterDto.User(username, email, password);
        return new UserRegisterDto(userFields);
    } 

    @Step("Генерируем рандомную публикацию")
    public static CreateArticleDto generateRandomArticle() {
        Set<String> tags = Set.of(
            "#" + faker.lorem().word() + System.nanoTime(),
            "#" + faker.lorem().word() + System.nanoTime()
        );

        CreateArticleDto.Article articleFields = new CreateArticleDto.Article(
            faker.lorem().sentence(5),
            faker.lorem().sentence(10),
            faker.lorem().paragraph(10),
            tags
        );
        return new CreateArticleDto(articleFields);
    }
}
