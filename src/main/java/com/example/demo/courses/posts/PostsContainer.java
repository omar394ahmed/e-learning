package com.example.demo.courses.posts;

import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
@ApiIgnore
public class PostsContainer {

    private String coursename ;
    private List<PostDTO> courseposts ;

    public PostsContainer(final String coursename, final List<PostDTO> courseposts) {
        this.coursename = coursename;
        this.courseposts = courseposts;
    }

    public String getCoursename() {
        return this.coursename;
    }

    public void setCoursename(final String coursename) {
        this.coursename = coursename;
    }

    public List<PostDTO> getCourseposts() {
        return this.courseposts;
    }

    public void setCourseposts(final List<PostDTO> courseposts) {
        this.courseposts = courseposts;
    }
}
