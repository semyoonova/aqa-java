package com.aqa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.aqa.api.models.UserLoginDto;
import com.aqa.api.models.clients.ArticleClient;
import com.aqa.api.models.clients.UserClient;
import com.aqa.utils.TestDataGenerator;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

public class CreateArticleTest extends BaseTest {
    
    private String token;
    private final UserClient userClient = new UserClient();
    private final ArticleClient articleClient = new ArticleClient();

    @BeforeEach
    public void prepareUserAndGetToken() {
        var randomUser = TestDataGenerator.generateRandomUser();
        
        userClient.register(randomUser);

        UserLoginDto.User loginFields = new UserLoginDto.User(
            randomUser.getUser().getEmail(), 
            randomUser.getUser().getPassword()
        );
        UserLoginDto loginBody = new UserLoginDto(loginFields);

        token = userClient.login(loginBody)
            .then()
            .statusCode(200)
            .extract()
            .path("user.token");
    }

    @Test
    @Story("Успешное создание публикации")
    @Description("Проверка того, что авторизованный пользователь может создать публикацию")
    public void userCanCreateArticleSuccessfully() {
        var articleBody = TestDataGenerator.generateRandomArticle();

        articleClient.createArticle(token, articleBody)
            .then()
                .statusCode(200); 
    }
}
