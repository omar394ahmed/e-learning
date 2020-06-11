package com.example.demo.users;


import com.example.demo.enrollments.RequestSet;

public class UserDTO {
    private Long id ;
    private  String username ;
    private String name ;

    private int join_status ;

    @Override
    public String toString() {
        return this.id + "  " +this.name + "  " + this.username;
    }

    public UserDTO() {
    }

    public UserDTO( Long id,  String name ,  String username ,  int join_status) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.join_status = join_status;
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

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getJoin_status() {
        return this.join_status;
    }

    public void setJoin_status(final int join_status) {
        this.join_status = join_status;
    }
}
