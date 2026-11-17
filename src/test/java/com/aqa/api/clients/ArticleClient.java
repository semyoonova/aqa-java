package com.aqa.api.clients;

import com.aqa.api.models.CreateArticleDto;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ArticleClient {
    @Step("API: Создаем новую публикацию")
    public Response createArticle(String token, CreateArticleDto articleDto) {
        return given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Token " + token) 
            .body(articleDto)
        .when()
            .log().all()
            .post("/articles");
    }

    @Step("API: Получаем публикации автора {1}")
    public Response getArticlesByAuthor(String token, String author) {
        return given()
            .header("Authorization", "Token " + token) 
            .param("author", author)
        .when()
            .log().all()
            .get("/articles");
    }
}
