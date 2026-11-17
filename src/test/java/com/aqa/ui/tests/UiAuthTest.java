package com.aqa.ui.tests;

import static com.aqa.utils.AppConfig.CONFIG;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.aqa.BaseTest;
import com.aqa.api.clients.UserClient;
import com.aqa.ui.pages.HomePage;
import com.aqa.ui.pages.LoginPage;
import com.aqa.utils.TestDataGenerator;

import io.qameta.allure.Epic;

@Epic("UI: Авторизация")
public class UiAuthTest extends BaseTest{
    
    @Test
    @DisplayName("Авторизация с невалидными данными")
    public void invalidDataAuth() {
        var email = "qwert@mail.ru";
        var password = "wert123!!";
        new LoginPage()
            .open()
            .login(email, password)
            .checkInvalidDataMessageIsVisible();
    }

    @Test
    @DisplayName("Авторизация с валидными данными")
    public void validDataAuth() {
        
        var client = new UserClient(CONFIG.remoteApiBaseUrl());
        var user = TestDataGenerator.generateRandomUser();
        client.register(user).then().statusCode(201);

        new LoginPage()
            .open()
            .login(user.getUser().getEmail(), user.getUser().getPassword());

        new HomePage()
            .isLoaded()
            .checkUserIsSignIn(user.getUser().getUsername());
    }
}
