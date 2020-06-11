package com.example.demo.enrollments;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@SuppressWarnings("ALL")
@Embeddable
public class St_Co_CompositeKey implements Serializable {

    @Column(name = "student_id")
    private  Long student_id ;

    @Column(name = "course_id")
    private  int course_id ;

    public  St_Co_CompositeKey(){

    }

    public St_Co_CompositeKey(Long student_id, int course_id) {
        this.student_id = student_id;
        this.course_id = course_id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
