package com.example.liftaday.utils.news;

import java.util.List;

public class NewsList {
    List<NewsArticle> articles;

    public NewsList() {
    }

    public NewsList(List<NewsArticle> article) {
        this.articles = article;
    }

    public List<NewsArticle> getArticle() {
        return articles;
    }

    public void setArticle(List<NewsArticle> article) {
        this.articles = article;
    }
}
