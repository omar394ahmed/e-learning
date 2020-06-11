package com.example.demo.courses.posts;
/*
import com.example.demo.courses.CourseModel;
import com.example.demo.courses.posts.comments.CommentModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.grpc.StatusRuntimeException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;
//import com.example.demo.courses.posts.comments.CommentModel;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;


@SuppressWarnings("ALL")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;
    private String Author ;
    private String date ;
    private int up ;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private CourseModel course ;
    @OneToMany(mappedBy = "question" , fetch = FetchType.EAGER)
    private List<CommentModel> Comments ;



    private String content ;

    public List<CommentModel> getComments() {
        return Comments;
    }

    public void setComments(List<CommentModel> comments) {
        Comments = comments;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }
}
*/
