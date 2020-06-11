package com.example.demo.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Column;
@JsonPropertyOrder({"id" , "name" , "username", "PhoneNumber", "teacher", "grade", "authority", "token"})
public class ProfileDTO {

    private Long id;

    private String username;

    private String name;

    private String PhoneNumber;
    private String authority;
    private String grade;

    private Boolean teacher ;

    private  String token ;

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

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.PhoneNumber = phoneNumber;
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

    public Boolean getTeacher() {
        return this.teacher;
    }

    public void setTeacher(final Boolean teacher) {
        this.teacher = teacher;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
