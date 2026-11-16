package com.aqa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selectors.*;

public class LoginPage extends BasePage<LoginPage> {
    private static final String PAGE_URL = "/login";
    private final SelenideElement emailInput = $("input[formcontrolname='email']");
    private final SelenideElement passwordInput = $("input[formcontrolname='password']");
    private final SelenideElement submitButton = $("button[type='submit']");
    //TODO: поправить локатор 
    private final SelenideElement errorList = $("app-list-errors");

    public LoginPage login(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        submitButton.click();
        return this;
    }

    @Override
    public LoginPage isLoaded() {
        submitButton.shouldBe(Condition.visible);
        return this;
    }

    @Override
    public LoginPage open() {
        Selenide.open(PAGE_URL);
        return isLoaded();
    }

    public LoginPage checkInvalidDataMessageIsVisible() {
        errorList.find(byText( "email or password is invalid")).shouldBe(Condition.visible);
        return this;
    }
}
