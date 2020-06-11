package com.example.demo.courses.posts;

import com.example.demo.courses.CourseModel;
import com.example.demo.courses.courseRepository;
import com.example.demo.firebase.FirebaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/*
@Service
public class PostService {

    @Autowired
    PostRepository postRepo ;
    @Autowired
    FirebaseService firebaseservice ;
    @Autowired
    courseRepository  courseRepo ;
  */
/*  public PostDTO savePost(String courseid, PostModel post) throws InterruptedException, ExecutionException, IOException {

        Optional<CourseModel> course = courseRepo.findById(courseid);
        post.setCourse(course.get());
        postRepo.save(post);
        String instructorName = course.get().getInstructor().getUsername();
        ModelMapper mapper = new ModelMapper();
        PostDTO simplepost = mapper.map(post , PostDTO.class);
        firebaseservice.CreatPost(courseid , simplepost , instructorName);

        return simplepost ;

    }

    public List<PostDTO> getposts(String courseid) throws ExecutionException, InterruptedException {

        PostDTO simplepost = new PostDTO();
        ModelMapper mapper = new ModelMapper();

        String instructorName =  courseRepo.findById(courseid).get().getInstructor().getUsername();

        List<PostModel> detailsPostList =  firebaseservice.getposts(courseid , instructorName );
        return  detailsPostList.stream().map(postModel -> mapper.map(postModel , simplepost.getClass())).collect(Collectors.toList());
    }*//*

}
*/
