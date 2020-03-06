package com.example.liftaday.utils.news;

public class NewsArticle {
    String title;
    String description;
    String url;
    String content;
    String urlToImage;

    public NewsArticle() {
    }

    public NewsArticle(String title, String description, String url, String content,String urlToImage) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.content = content;
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
