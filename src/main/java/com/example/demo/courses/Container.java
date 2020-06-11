package com.example.demo.courses;

import com.example.demo.enrollments.RequestSet;
import org.springframework.boot.jackson.JsonComponent;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@ApiIgnore
public class Container {

    private CourseModel course;
    private RequestSet join_status;

    public Container(final CourseModel course, final RequestSet join_status) {

        this.course = course;
        this.join_status = join_status;
    }

    public CourseModel getCourse() {
        return this.course;
    }

    public void setCourse(final CourseModel course) {
        this.course = course;
    }

    public RequestSet getJoin_status() {
        return this.join_status;
    }

    public void setJoin_status(final RequestSet join_status) {
        this.join_status = join_status;
    }
}
