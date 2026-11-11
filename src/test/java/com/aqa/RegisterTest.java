package com.aqa;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.aqa.api.clients.UserClient;
import com.aqa.api.models.UserRegisterDto;
import com.aqa.utils.TestDataGenerator;
import org.hamcrest.Matchers;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Управление пользователями")
@Feature("Регистрация")
public class RegisterTest extends BaseTest {
    
    @Test
    @Story("Успешная регистрация")
    @Description("Проверка того, что пользователь может успешно зарегистрироваться с валидными данными")
    public void registerUserShouldReturnCode201() {
        var client = new UserClient();
        client.register(TestDataGenerator.generateRandomUser())
        .then()
            .log().all()
            .statusCode(201);        
    }

    @Test
    @Story("Негативные сценарии регистрации")
    @Description("Проверка повторной регистрации")
    public void registerDublicateUserShouldFail() {
        var user = TestDataGenerator.generateRandomUser();
        var client = new UserClient();
        client.register(user)
        .then()
            .log().all()
            .statusCode(201);
        
        client.register(user)
        .then()
            .log().all()
            .statusCode(422);       
    }
    
    @ParameterizedTest(name = "Регистрация должна падать, если {0}")
    @MethodSource("getInvalidUsers")
    @Story("Негативные сценарии регистрации")
    @Description("Проверка валидации полей при регистрации с пустыми данными")
    public void registerUserWithEmptyFieldShouldFail(String testName, UserRegisterDto invalidUser, String errorField, String expectedError) {
        var client = new UserClient();
        client.register(invalidUser)
        .then()
            .log().all()
            .statusCode(422) 
            .body("errors." + errorField, Matchers.hasItem(expectedError)); 
    }

    private static Stream<Arguments> getInvalidUsers() {
        var userEmptyName = TestDataGenerator.generateRandomUser();
        userEmptyName.getUser().setUsername("");

        var userEmptyEmail = TestDataGenerator.generateRandomUser();
        userEmptyEmail.getUser().setEmail("");

        var userEmptyPassword = TestDataGenerator.generateRandomUser();
        userEmptyPassword.getUser().setPassword("");

        return Stream.of(
            Arguments.of("пустой username", userEmptyName, "username", "can't be empty"),
            Arguments.of("пустой email", userEmptyEmail, "email", "can't be empty"),
            Arguments.of("пустой пароль", userEmptyPassword, "password", "can't be empty")
        );
    }
}
