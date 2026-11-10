package com.aqa.api.models.clients;

import com.aqa.api.models.CreateArticleDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ArticleClient {
    public Response createArticle(String token, CreateArticleDto articleDto) {
        return given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Token " + token) 
            .body(articleDto)
        .when()
            .post("/articles");
    }
}
