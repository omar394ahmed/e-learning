package com.example.demo;

import com.example.demo.courses.*;
import com.example.demo.enrollments.Enrollment;
import com.example.demo.enrollments.RequestsContainer;
import com.example.demo.exceptions.ResponsePayload;
import com.example.demo.users.UserDTO;
import com.example.demo.users.UserServcie;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/courses", produces = "application/json")
public class CourseResource {

    @Autowired
    CourseService courseService;

    @Autowired
    UserServcie userServcie;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ResponseEntity<ResponsePayload> welocome() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsePayload(HttpStatus.OK.toString(), "<h1>Et3lm App is UP You can Use IT</h1>"));
    }

   /* @RequestMapping("/")
    public UserController getUserController(){
        return new UserController();
    }*/

    @RequestMapping(path = "/all" , method = RequestMethod.GET)
    public Set<CourseModel> getCourses(@RequestParam("id") long id) throws ExecutionException, InterruptedException {
        return courseService.getCourses(id);
    }

    @ApiOperation(tags = "Student_apis", value = "this api request join new course ")
    @RequestMapping(path = "{courseid}/join", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponsePayload> JoinCourse(@RequestBody(required = true) Enrollment entrollment) {
        String ConfirmMessage = courseService.joinCourse(entrollment);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsePayload(HttpStatus.OK.toString(), ConfirmMessage));
    }


    @RequestMapping(path = "{courseid}/confirmjoin", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(tags = "Student_apis", value = "you have yo add code and course id and user id to let us confirm user code")
    public ResponseEntity<ResponsePayload> confirmJoin(@PathVariable int courseid,
                                                       @RequestParam() Long userid,
                                                       @RequestParam() int code) {
        String message = courseService.confirmJoinCourse(courseid, userid, code);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsePayload(HttpStatus.OK.toString(), message));
    }

    @RequestMapping(path = "{userid}", method = RequestMethod.GET)
    @ApiOperation(tags = "Student_apis" , value =  "get course you join ar request to join ")
    public ResponseEntity<List<Container>> getstudentCourses(@PathVariable("userid") Long userid) throws ExecutionException, InterruptedException {
        List<Container> courses = courseService.getstudentCourses(userid);
        return ResponseEntity.status(HttpStatus.OK).body(courses);

    }
/*

    @RequestMapping(path = "/{courseid}", method = RequestMethod.GET, produces = "application/json")
    public CourseModel getCourse(@PathVariable("courseid") String id, @RequestParam(value = "tag", required = true) Optional<String> tag) throws ExecutionException, InterruptedException {
        return courseService.getCourse(id, tag);
    }
*/


    /*@RequestMapping(path = "/{courseid}/posts", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponsePayload> publishNewPost(@PathVariable("courseid") String courseid, @RequestBody
            PostModel post) throws InterruptedException, ExecutionException, IOException {

        postService.savePost(courseid, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponsePayload(HttpStatus.CREATED.toString(), "POST Created on " + courseid));
    }
*/
    /*@RequestMapping(path = "/{courseid}/posts", method = RequestMethod.GET, produces = "application/json")
    public PostsContainer getCoursePosts(@PathVariable("courseid") String courseid) throws InterruptedException, ExecutionException, IOException {

        PostsContainer contrainer = new PostsContainer(courseid, postService.getposts(courseid));
        return contrainer;
    }
*/
   /* @RequestMapping(path = "/{courseid}/posts/{postid}/comments", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ResponsePayload> addComment(@PathVariable("courseid") String courseid,
                                                      @PathVariable("postid") String postid,
                                                      @RequestBody CommentModel comment) throws ExecutionException, InterruptedException {

        commentservice.addComment(courseid, postid, comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponsePayload(HttpStatus.CREATED.toString(), "add Comment to " + postid));
    }*/

    /* @RequestMapping(value = "/{courseid}/posts/{postid}/comments", method = RequestMethod.GET, produces = "application/json")
     public List<CommentDTO> getPostComments(@PathVariable("courseid") String courseid,
                                             @PathVariable("postid") String postid
     ) throws ExecutionException, InterruptedException {
         return commentservice.getComments(courseid, postid);
     }*/
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(tags = "Intructor_apis", value = "this api creat new Course",
            notes = "in new update this api will Check if the person is teacher or not but know all users can creat Course ")
    public ResponseEntity<ResponsePayload> creatCourse(@NotNull @RequestBody CourseModel course) throws ExecutionException, InterruptedException {
        courseService.creatCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponsePayload(HttpStatus.CREATED.toString(), "Course is Created Successfully you Can Access It Know with /courses/" + course.getName()));
    }


    @RequestMapping(path = "/created", method = RequestMethod.GET)
    @ApiOperation(tags = "Intructor_apis", value = "get instructor Course created by him  ", notes = "you can now put token without Bearer prefix ebst ya3m")
    public List<CourseModel> getCoursesUserCreated(@RequestHeader(value = "Authorization", required = true) String token) throws ExecutionException, InterruptedException {

        return courseService.getCoursesCreated(token);
    }

    @ApiOperation(tags = "Intructor_apis", value = "update course data ")
    @RequestMapping(path = "/created/{courseid}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<ResponsePayload> updateCourse(@NotNull @RequestBody CourseModel course) throws ExecutionException, InterruptedException, InvocationTargetException, IllegalAccessException {
        String message = courseService.UpdateCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponsePayload(HttpStatus.CREATED.toString(), message));
    }


    @RequestMapping(path = "/created/{courseid}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Intructor_apis", notes = "you can now put token without Bearer prefix ebst ya3m", value = "Course  Delete Method")
    public ResponseEntity<ResponsePayload> deleteCourse(
            @RequestHeader(value = "Authorization", required = true) String token,
            @PathVariable() int courseid
           ) throws ExecutionException, InterruptedException {

        String message = courseService.deleteCourse(courseid, token);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsePayload(HttpStatus.OK.toString(), message));
    }

    @ApiOperation(tags = "Intructor_apis", value = "this api return course waiting to join students ")
    @RequestMapping(path = "/created/{courseid}/waitingStudents", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getCourseRequests(@PathVariable(required = true) int courseid) {
        List<UserDTO> requets = courseService.getWatingList(courseid);
        return ResponseEntity.status(HttpStatus.OK).body(requets);
    }

    @ApiOperation(tags = "Intructor_apis", value = "this api return course students ")
    @RequestMapping(path = "/created/{courseid}/joinedstudents", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getCourseStudents(@PathVariable() int courseid) {
        List<UserDTO> users = courseService.getStudentOfCourse(courseid);
        return ResponseEntity.status(HttpStatus.FOUND).body(users);
    }

    @ApiOperation(tags = "Intructor_apis", value = "this api accept list of accepted student join requests")
    @RequestMapping(path = "/created/{courseid}/acceptList", method = RequestMethod.POST)
    public ResponseEntity<ResponsePayload> AcceptStudentRequests(@PathVariable() int courseid,
                                                                 @RequestBody() RequestsContainer contrainer) {
        contrainer.setCourseid(courseid);
        String message = courseService.acceptRequest(contrainer);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsePayload(HttpStatus.OK.toString(), message));
    }

    @ApiOperation(tags = "Intructor_apis", value = "this api delete request")
    @RequestMapping(path = "/created/{courseid}/waitlist/{userid}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponsePayload> deleteRequest(@PathVariable() int courseid,
                                                         @PathVariable() long userid) {

        String message = courseService.daleteStudentRequest(courseid, userid);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsePayload(HttpStatus.OK.toString(), message));

    }


}

