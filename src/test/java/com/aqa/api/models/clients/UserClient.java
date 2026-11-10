package com.aqa.api.models.clients;

import com.aqa.api.models.UserLoginDto;
import com.aqa.api.models.UserRegisterDto;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserClient {
    
    public Response register(UserRegisterDto userDto) {
        return given()
            .contentType(ContentType.JSON)
            .body(userDto)
        .when()
            .post("/users");
    }

    public Response login(UserLoginDto loginDto) {
        return given()
            .contentType(ContentType.JSON)
            .body(loginDto)
        .when()
            .post("/users/login");
    }

    public String getLoginToken(UserLoginDto loginDto) {
        return login(loginDto)
            .then()
            .statusCode(200)
            .extract()
            .path("user.token");
    }
}