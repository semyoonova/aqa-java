package com.aqa;

import org.junit.jupiter.api.Test;

import com.aqa.api.models.UserRegisterDto;
import com.aqa.utils.TestDataGenerator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RegisterTest extends BaseTest {
    
    @Test
    public void registerUser() {
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
}
