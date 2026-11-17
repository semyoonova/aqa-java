package com.aqa.ui.pages;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selectors.*;

public class LoginPage extends BasePage<LoginPage> {
    private static final String PAGE_URL = "/login";
    private final SelenideElement emailInput = $("input[formcontrolname='email']");
    private final SelenideElement passwordInput = $("input[formcontrolname='password']");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement errorList = $("app-list-errors");

    @Step("UI: Авторизация пользователя {0}")
    public LoginPage login(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        submitButton.click();
        return this;
    }

    @Step("UI: Проверяем загрузку страницы")
    @Override
    public LoginPage isLoaded() {
        submitButton.shouldBe(Condition.visible);
        return this;
    }

    @Step("UI: Открываем страницу с авторизацией")
    @Override
    public LoginPage open() {
        Selenide.open(PAGE_URL);
        return isLoaded();
    }

    @Step("UI: Проверяем наличие сообщения с ошибкой")
    public LoginPage checkInvalidDataMessageIsVisible() {
        errorList.find(byText( "email or password is invalid")).shouldBe(Condition.visible);
        return this;
    }
}
