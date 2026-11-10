package com.aqa;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.aqa.api.models.UserRegisterDto;
import com.aqa.utils.TestDataGenerator;
import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RegisterTest extends BaseTest {
    
    @Test
    public void registerUserShouldReturnCode201() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body(TestDataGenerator.generateRandomUser())
            .when()
                .post("/users")
            .then()
                .log().all()
                .statusCode(201);        
    }

    @Test
    public void registerDublicateUserShouldFail() {
        var user = TestDataGenerator.generateRandomUser();
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body(user)
            .when()
                .post("/users")
            .then()
                .log().all()
                .statusCode(201); 
        
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body(user)
            .when()
                .post("/users")
            .then()
                .log().all()
                .statusCode(422);         
    }
    
    @ParameterizedTest(name = "Регистрация должна падать, если {0}")
    @MethodSource("getInvalidUsers")
    public void registerUserWithEmptyFieldShouldFail(String testName, UserRegisterDto invalidUser, String errorField, String expectedError) {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body(invalidUser)
            .when()
                .post("/users")
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
