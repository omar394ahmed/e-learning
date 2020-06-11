package com.example.demo.courses;


import com.example.demo.courses.media.Image;
import com.example.demo.courses.media.VideoModel;
import com.example.demo.enrollments.Enrollment;
import com.example.demo.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
@NamedQuery(name = "CourseModel.getStudentCourses",
        query = "select new com.example.demo.courses.Container(course , enroll.join_status)  from " +
                "CourseModel  course inner  join Enrollment  enroll  on enroll.course.id = course.id " +
                " where enroll.join_status in (:status1 ,  :status2 , :status3) and  enroll.user.id = :id "

)
@NamedQuery(name = "CourseModel.getAllCourses",
        query = "select course from  CourseModel  course where " +
                "course.instructor.id  not like  :id and course.id not in (select e.key.course_id from Enrollment e where  e.key.student_id = :id ) "
)
@Entity
@JsonPropertyOrder({"tag", "name", "overview", "ratings", "studentNumber", "started_date", "instructor", "videos"
        , "images"})
@JsonIgnoreProperties(value={ "images" , "videos" }, allowGetters=true , allowSetters = false)
public class CourseModel implements Serializable {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Instructor", referencedColumnName = "name")
    private User instructor;

    private int ratings;
    private String started_date;

    private String Overview;
    private int studentNumber;

    @Transient
    private List<VideoModel> videos = new ArrayList<>();


    @Transient
    private List<Image> images = new ArrayList<>();


    /*@OneToMany(mappedBy = "course")
    //@Fetch(FetchMode.SUBSELECT)
    private List<PostModel> courseQuestions;
    */
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Enrollment> enrollment;

    @Override
    public String toString() {
        return "the course data is  : "+id+" "+name+" "+Overview;
    }


    public CourseModel() {

    }

    private String tag;


    public List<VideoModel> getVideos() {
        return videos;
    }


    public void setVideos(List<VideoModel> videos) {
        this.videos = videos;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getStarted_date() {
        return started_date;
    }

    public void setStarted_date(String started_date) {
        this.started_date = started_date;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }


    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber() {
        this.studentNumber = getStudentNumber() + 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public List<Enrollment> getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(List<Enrollment> enrollment) {
        this.enrollment = enrollment;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


}
