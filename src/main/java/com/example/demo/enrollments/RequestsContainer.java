package com.example.demo.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

public class RequestsContainer {
    @JsonIgnore
    private int courseid;

    Map<Long, String> userids;


    public RequestsContainer() {
    }

    public Map<Long, String> getUserids() {
        return this.userids;
    }

    public void setUserids(final Map<Long, String> userids) {
        this.userids = userids;
    }

    public int getCourseid() {
        return this.courseid;
    }

    public void setCourseid(final int courseid) {
        this.courseid = courseid;
    }


}
