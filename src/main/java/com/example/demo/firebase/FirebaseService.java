package com.example.demo.firebase;

import com.example.demo.courses.CourseDTO;
import com.example.demo.courses.posts.comments.CommentDTO;
import com.example.demo.courses.media.VideoModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/*import com.auth0.jwt.*;*/

@Service
public class FirebaseService {

    public List<CourseDTO> getCourses() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        List<CourseDTO> courses = new ArrayList<>();
        CollectionReference instructors = firestore.collection("instructors/");
        instructors.listDocuments().forEach(documentReference -> {
            documentReference.collection("Courses/").listDocuments().forEach(documentReference1 -> {
                try {
                    courses.add(documentReference1.get().get().toObject(CourseDTO.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        });
        return  courses ;
       /* Map<String, List<CourseDTO>> instructorCourses = new HashMap<>();

        instructors.listDocuments().forEach(documentReference -> {
            List<CourseDTO> courses = new ArrayList<>();
            documentReference.collection("Courses").listDocuments().forEach(documentReference1 -> {
                try {
                    courses.add(documentReference1.get().get().toObject(CourseDTO.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            instructorCourses.put(documentReference.getId(), courses);
        });
        return instructorCourses ;*/


    }


    public String CreatCourse(Integer courseid, CourseDTO course, String instructor) throws ExecutionException, InterruptedException, IOException {

        String  id = courseid.toString();
        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApiFeature = firestore.collection("instructors/" + instructor + "/Courses/").document(id).set(course);
        return collectionApiFeature.get().getUpdateTime().toString();
    }


    public CourseDTO getCourse (String instructorName  , String courseId) throws ExecutionException, InterruptedException {

        Firestore firestore = FirestoreClient.getFirestore();
        return firestore.collection("instructors/"+instructorName+"/Courses").
                document(courseId).get().get().toObject(CourseDTO.class);
    }

    public String addVideo(String courseid , VideoModel video) {
        Map<String , VideoModel> videos =  new HashMap<>();
        videos.put("videos" , video );
        Firestore firestore = FirestoreClient.getFirestore();
         firestore.collection("instructors/omar312ahmed@gmail.com/Courses").
                document(courseid).set(videos , SetOptions.merge());
         return "Successfully addedd " ;
    }

    public void deleteCourse(String instructor, String cousreName) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = firestore.collection("instructors/"+instructor+"/Courses/").document(cousreName).delete();
// ...
        System.out.println("Update time : " + writeResult.get().getUpdateTime());
    }







   /* public String CreatPost(String courseid, PostDTO post, String instructor) throws ExecutionException, InterruptedException, IOException {

        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFeature = firestore.collection("instructors/" + instructor + "/Courses/" + courseid + "/posts/").document(String.valueOf(post.getId())).set(post);
        // CollectionReference reference = firestore.document("instructors/"+instructor+"/Courses/"+courseid).collection("/posts").
        return collectionApiFeature.get().getUpdateTime().toString();
    }

    public List<PostModel> getposts(String courseid, String instructor) throws InterruptedException, ExecutionException {

        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collection = firestore.collection("instructors/" + instructor + "/Courses/" + courseid + "/posts");
        List<PostModel> posts = new ArrayList<PostModel>();
        collection.listDocuments().forEach(documentReference -> {
            try {
                posts.add(documentReference.get().get().toObject(PostModel.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        return posts;


    }

    public String addComment(String courseid, String postId, CommentDTO comment, String instructor) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFeature = firestore.collection("instructors/" + instructor + "/Courses/" + courseid + "/posts/" + postId + "/comments").document(String.valueOf(comment.getId())).set(comment);
        return collectionApiFeature.get().getUpdateTime().toString();
    }

    public List<CommentDTO> getComments(String courseid, String postid, String instructor) throws InterruptedException, ExecutionException {

        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collection = firestore.collection("instructors/" + instructor + "/Courses/" + courseid + "/posts/" + postid + "/comments/");
        List<CommentDTO> comments = new ArrayList<CommentDTO>();
        collection.listDocuments().forEach(documentReference -> {
            try {
                comments.add(documentReference.get().get().toObject(CommentDTO.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        return comments;


    }
*/

}
