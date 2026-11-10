package com.aqa.api.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class CreateArticleDto {
    
    private Article article;

    @Data
    @AllArgsConstructor 
    @NoArgsConstructor 
    public static class Article {
        private String title;
        private String description;
        private String body;
        private ArrayList<String> tagList;
    }
}
