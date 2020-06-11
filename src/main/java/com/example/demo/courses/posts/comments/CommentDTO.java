package com.example.demo.courses.posts.comments;

import javax.persistence.Id;

public class CommentDTO {

    private int id ;
    private String Author ;
    private String Content ;
    private String date ;
    private  int rate ;

    public CommentDTO() {
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

    public String getContent() {
        return this.Content;
    }

    public void setContent(final String content) {
        this.Content = content;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }
}

