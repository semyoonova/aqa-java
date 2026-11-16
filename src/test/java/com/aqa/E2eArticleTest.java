package com.aqa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.aqa.utils.DbHelper;
import com.aqa.utils.TestDataGenerator;

import com.aqa.api.clients.ArticleClient;
import com.aqa.api.clients.UserClient;
import com.aqa.api.models.CreateArticleDto;
import com.aqa.api.models.UserLoginDto;
import com.aqa.api.models.UserRegisterDto;

public class E2eArticleTest extends BaseTest{
    
    @Test
    public void createArticleAndGetById() {
        var userClient = new UserClient();
        var articleClient = new ArticleClient();
        UserRegisterDto user = TestDataGenerator.generateRandomUser();
        CreateArticleDto article = TestDataGenerator.generateRandomArticle();

        UserLoginDto.User userDto = new UserLoginDto.User(user.getUser().getEmail(), user.getUser().getPassword());
        UserLoginDto loginUser = new UserLoginDto(userDto);

        userClient.register(user);
        String token = userClient.login(loginUser)
        .then()
            .extract()
                .path("user.token");
        
        articleClient.createArticle(token, article)
        .then()
            .statusCode(200);
        
        var responseArticle = articleClient.getArticlesByAuthor(token, user.getUser().getUsername())
        .then()
            .statusCode(200)
            .log().body()
            .extract()
                .jsonPath()
                .getObject("articles[0]", CreateArticleDto.Article.class);
        
        
        Assertions.assertAll( () ->
            {
                Assertions.assertEquals(article.getArticle().getTitle(), responseArticle.getTitle());
                Assertions.assertEquals(article.getArticle().getDescription(), responseArticle.getDescription());
                Assertions.assertEquals(article.getArticle().getBody(), responseArticle.getBody());
                // TODO: дефект: только уникальные теги приходят в get запросе, возможно нужна задержка 
                Assertions.assertEquals(article.getArticle().getTagList(), responseArticle.getTagList());
            }
        );
    }

    @Test
    public void registeredUserAndCreatedArticleShouldBeInsertedIntoDB() {
        var userClient = new UserClient();
        var articleClient = new ArticleClient();
        UserRegisterDto user = TestDataGenerator.generateRandomUser();
        CreateArticleDto article = TestDataGenerator.generateRandomArticle();

        UserLoginDto.User userDto = new UserLoginDto.User(user.getUser().getEmail(), user.getUser().getPassword());
        UserLoginDto loginUser = new UserLoginDto(userDto);

        userClient.register(user).then().statusCode(201);
        
        Assertions.assertTrue(DbHelper.isUserExistsInDb(user.getUser().getEmail()));

        String token = userClient.login(loginUser)
        .then()
            .extract()
                .path("user.token");
        
        articleClient.createArticle(token, article)
        .then()
            .statusCode(200);
        
        var responseArticle = articleClient.getArticlesByAuthor(token, user.getUser().getUsername())
        .then()
            .statusCode(200)
            .log().body()
            .extract()
                .jsonPath()
                .getObject("articles[0]", CreateArticleDto.Article.class);
        
        Assertions.assertTrue(DbHelper.isArticleExistsInDb(article.getArticle().getTitle()));
        
    }
}
