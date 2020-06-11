package com.example.demo.courses.posts;

import io.swagger.annotations.ApiModel;


public class PostDTO {

    private int id ;
    private String Author ;
    private String date ;
    private int up ;
    private String content  ;

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public PostDTO() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getAuthor() {
        return this.Author;
    }

    public void setAuthor(final String author) {
        this.Author = author;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public int getUp() {
        return this.up;
    }

    public void setUp(final int up) {
        this.up = up;
    }
}
