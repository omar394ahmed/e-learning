package com.example.demo.courses.posts.comments;

import com.example.demo.courses.courseRepository;
import com.example.demo.courses.posts.PostModel;
import com.example.demo.courses.posts.PostRepository;
import com.example.demo.firebase.FirebaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
/*

@Service
public class CommentService {
*/
/*

    @Autowired
    CommentRepository commentRepo ;
     @Autowired
    PostRepository postRepo ;

     @Autowired
     courseRepository courseRepo ;

     @Autowired
     FirebaseService firebaseService ;
    public  String addComment(String courseid, String postId, CommentModel comment) throws ExecutionException, InterruptedException {
        int id = Integer.parseInt(postId);
        System.out.println(id);
        Optional<PostModel> post = postRepo.findById(id);
        post.orElseThrow(() -> new NoSuchElementException("there is NO Post with that id "+ postId));
        comment.setQuestion(post.get());
        // save comment  to Mysql
        commentRepo.save(comment);

        ModelMapper mapper = new ModelMapper();
        CommentDTO abstract_comment = mapper.map(comment , CommentDTO.class);
        // save Comment to Firebase

       String instructorName = courseRepo.findById(courseid).get().getInstructor().getUsername();
       return firebaseService.addComment(courseid , postId , abstract_comment, instructorName );
           }

    public List<CommentDTO> getComments(String courseid, String postid) throws ExecutionException, InterruptedException {
        String instructorName = courseRepo.findById(courseid).get().getInstructor().getUsername();

        return  firebaseService.getComments(courseid , postid , instructorName);
    }

*//*


}
*/
