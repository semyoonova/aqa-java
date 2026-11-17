package com.aqa;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import static com.aqa.utils.AppConfig.CONFIG;


public class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = CONFIG.apiBaseUrl();
        Configuration.baseUrl = CONFIG.uiBaseUrl();
        
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 5000; 
        Configuration.headless = true;

        RestAssured.filters(new AllureRestAssured());

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
            .screenshots(true)
            .savePageSource(false));
    }
}

