package com.example.demo.courses;

import com.example.demo.courses.media.Image;
import com.example.demo.courses.media.VideoModel;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Note this is an shortcut of Course object used to ignore some properities")
public class CourseDTO {

    private int id;

    private List<VideoModel> Videos ;
    private List<Image> images ;

    public CourseDTO() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public List<VideoModel> getVideos() {
        return this.Videos;
    }

    public void setVideos(final List<VideoModel> videos) {
        this.Videos = videos;
    }



    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(final List<Image> images) {
        this.images = images;
    }


}
