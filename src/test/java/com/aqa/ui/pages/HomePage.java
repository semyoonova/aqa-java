package com.aqa.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.*;        
import static com.codeborne.selenide.Selectors.*;

public class HomePage extends BasePage<HomePage>{

    private final SelenideElement homeLink = $(byText("Home"));
    @Override
    public HomePage isLoaded() {
        homeLink.shouldHave(attribute("routerLinkActive", "active")).shouldBe(Condition.visible);
        return this;
    }

    @Override
    public HomePage open() {
        Selenide.open();
        return isLoaded();
    }

    public HomePage checkUserIsSignIn(String username) {
        SelenideElement nameLink = $("nav.navbar a.nav-link[href*='/profile/" + username + "']");
        nameLink.shouldBe(Condition.visible);
        return this;
    } 
    
}
