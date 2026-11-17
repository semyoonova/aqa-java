package com.aqa.api.clients;

import com.aqa.api.models.UserLoginDto;
import com.aqa.api.models.UserRegisterDto;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static com.aqa.utils.AppConfig.CONFIG;

public class UserClient {

    private final String baseUrl;

    public UserClient() {
        baseUrl = CONFIG.apiBaseUrl();
    }

    public UserClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    @Step("API: Регистрируем нового пользователя")
    public Response register(UserRegisterDto userDto) {
        return given()
            .baseUri(baseUrl)
            .contentType(ContentType.JSON)
            .body(userDto)
        .when()
            .post("/users");
    }

    @Step("API: Авторизовываем нового пользователя")
    public Response login(UserLoginDto loginDto) {
        return given()
            .baseUri(baseUrl)
            .contentType(ContentType.JSON)
            .body(loginDto)
        .when()
            .post("/users/login");
    }

    @Step("API: Получаем токен пользователя")
    public String getLoginToken(UserLoginDto loginDto) {
        return login(loginDto)
            .then()
            .statusCode(200)
            .extract()
            .path("user.token");
    }
}