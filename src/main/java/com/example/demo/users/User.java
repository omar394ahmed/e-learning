package com.example.demo.users;

import com.example.demo.courses.CourseModel;
import com.example.demo.enrollments.Enrollment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dom4j.QName;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "instructor")
    @JsonIgnore
    private List<CourseModel> courses;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Enrollment> enrollments;

    @Nullable
    private String PhoneNumber;
    private String name;

    @JsonIgnore
    private String authority = "ROLE_USER";
    private String grade;

    @Column(length = 10000)
    @JsonIgnore
    private String token;

    @JsonIgnore
    private int confirmationCode;

    @JsonIgnore
    private boolean verified = false;

    @JsonIgnore
    private boolean update_state;
    @JsonIgnore
    private Boolean teacher = false;



    public Boolean getTeacher() {
        return this.teacher;
    }

    public void setTeacher(final Boolean teacher) {
        this.teacher = teacher;
    }

    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(final String password) {
        this.password = password;
    }

    public List<Enrollment> getEnrollments() {
        return this.enrollments;
    }

    public void setEnrollments(final List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Nullable
    public String getPhoneNumber() {
        return this.PhoneNumber;
    }

    public void setPhoneNumber(@Nullable final String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(final String authority) {
        this.authority = authority;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(final String grade) {
        this.grade = grade;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public int getConfirmationCode() {
        return this.confirmationCode;
    }

    public void setConfirmationCode(final int confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public void setVerified(final boolean verified) {
        this.verified = verified;
    }

    public boolean isUpdate_state() {
        return this.update_state;
    }

    public void setUpdate_state(final boolean update_state) {
        this.update_state = update_state;
    }

    public List<CourseModel> getCourses() {
        return this.courses;
    }

    public void setCourses(final List<CourseModel> courses) {
        this.courses = courses;
    }
}
