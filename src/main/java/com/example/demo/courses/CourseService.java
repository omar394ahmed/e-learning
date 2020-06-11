package com.example.demo.courses;

import com.example.demo.enrollments.*;
import com.example.demo.firebase.FirebaseService;
import com.example.demo.users.User;
import com.example.demo.users.UserDTO;
import com.example.demo.users.UserRepository;
import com.example.demo.users.verificationMailService;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("ALL")
@Service
@Transactional
public class CourseService {
    @Autowired
    courseRepository repo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    EnrollmentRepo enrollmentRepo;
    @Autowired
    FirebaseService firebaseService;
    @Autowired
    verificationMailService verificationMailService;

    public String creatCourse(CourseModel course) {
        Long id = course.getInstructor().getId();
        Optional<User> user = userRepo.findById(id);
        user.orElseThrow(() -> new NoSuchElementException("invalid id "));
        course.setInstructor(user.get());
        repo.save(course);

        String instructorusername = course.getInstructor().getUsername();
        System.out.println("seralized object :\t images : " + course.getImages() + "\n" + "videos" + course.getVideos());

        ModelMapper mapper = new ModelMapper();
        CourseDTO dtoObject = new CourseDTO();

        System.out.println("Before Mapping \t DTO images : " + dtoObject.getImages() + "\n" + "DTO videos" + dtoObject.getVideos());

        mapper.map(course ,  dtoObject);

        System.out.println("Aftermapping \t DTO images : " + dtoObject.getImages() + "\n" + "DTO videos" + dtoObject.getVideos());

        try {
            return firebaseService.CreatCourse(course.getId(), dtoObject, instructorusername);
        } catch (InterruptedException e) {
            return "exception in creating course firebase service " + e.toString();
        } catch (ExecutionException | IOException e) {
            return "exception in creating course firbase service " + e.toString();
        } catch (RuntimeException e) {
            return "exception in creating course firbase service " + e.toString();


        }
    }

    public String UpdateCourse(CourseModel newcourse) throws InvocationTargetException, IllegalAccessException {
        Optional<CourseModel> oldcourse = repo.findById(newcourse.getId());
        oldcourse.orElseThrow(() -> new NoSuchElementException("invalid course id "));
        System.out.println("the passed object : " + newcourse);
        nullAwareBeanCopy(newcourse, oldcourse.get());
        System.out.println("after Update : " + oldcourse.get());
        repo.save(oldcourse.get());
        return "course Updated Successfully";
    }


    public void getCourse(int id) {
        Optional<CourseModel> model = repo.findById(id);
        System.out.println(model.get().toString());
    }

    public List<UserDTO> getWatingList(int courseid) {
        List<UserDTO> requests = enrollmentRepo.getcourseStudents(courseid, RequestSet.waiting.value);
        return requests;
    }

    public List<UserDTO> getStudentOfCourse(int courseid) {
        List<UserDTO> requests = enrollmentRepo.getcourseStudents(courseid, RequestSet.Accepted.value);
        return requests;
    }


    public Set<CourseModel> getCourses(long userid) throws ExecutionException, InterruptedException {

       // List<CourseDTO> firebaseCourses = firebaseService.getCourses();
        Optional<User>  user = userRepo.findById(userid);
        user.orElseThrow(() -> new NoSuchElementException("invalid user id "));
        Set<CourseModel> mysqlCourses = repo.getAllCourses(userid);

        /*Set<CourseModel> updated = new HashSet<>();

        firebaseCourses.stream().forEach(courseDTO -> {
            mysqlCourses.stream().forEach(courseModel -> {
                if (courseDTO.getId() == courseModel.getId()) {
                    BeanUtils.copyProperties(courseDTO, courseModel);
                    updated.add(courseModel);
                    System.out.println("matching : mysql size  " + mysqlCourses.size() + " firebase size  : " + firebaseCourses.size());
                }
            });
        });
*/
        return mysqlCourses;

    }


    public static void nullAwareBeanCopy(Object source, Object dest) throws IllegalAccessException, InvocationTargetException {
        new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                System.out.println(" name : " + name + " value : " + value);
                if (value != null) {
                    super.copyProperty(dest, name, value);
                }
            }
        }.copyProperties(dest, source);
    }


    public String acceptRequest(RequestsContainer container) {
        int courseid = container.getCourseid();
        String courseName = repo.findById(courseid).get().getName();
        container.getUserids().entrySet().stream().forEach(id -> {
            Optional<Enrollment> userEnrollment = enrollmentRepo.findById(new St_Co_CompositeKey(id.getKey(), courseid));
            String Message = "this your code to JOIN " + courseName + " Course #";
            int code = Generate_and_sendCode(id.getValue(), Message, "Mail From Edres application to Join " + courseName + " course");
            userEnrollment.get().setJoin_status(RequestSet.Pending);
            userEnrollment.get().setJoin_code(code);
            enrollmentRepo.save(userEnrollment.get());

        });
        return "requests Accepted and send activation code ";
    }

    public String daleteStudentRequest(int courseid, long userid) {
        St_Co_CompositeKey key = new St_Co_CompositeKey(userid, courseid);
        enrollmentRepo.deleteById(key);
        return "Reuest Deleted ";
    }

    /*public CourseModel getCourse(String id, Optional<String> tag) throws ExecutionException, InterruptedException {
        Optional<CourseModel> selectedCourse;

        if (tag.isPresent()) {
            String tagname = tag.get();
            selectedCourse = repo.findByTagAndName(tagname, id);
        }
        selectedCourse = repo.findById(id);

        selectedCourse.orElseThrow(() -> {
            return new NoSuchElementException("there is No Course with this name insure you write the name correct");
        });

        CourseModel mysqlCourse = selectedCourse.get();
        CourseDTO firebaseCourse = firebaseService.getCourse(mysqlCourse.getInstructor().getUsername() , id);
        BeanUtils.copyProperties(firebaseCourse , mysqlCourse);

        repo.save(mysqlCourse);


        return mysqlCourse;
    }*/


    public String joinCourse(Enrollment enrollment) {
        Optional<Enrollment> e = enrollmentRepo.findById(enrollment.getKey());
        if (!e.isPresent()) {

            Optional<User> user = userRepo.findById(enrollment.getKey().getStudent_id());
            user.orElseThrow(() -> new NoSuchElementException("invalid user id "));
            enrollment.setUser(user.get());
            Optional<CourseModel> course = repo.findById(enrollment.getKey().getCourse_id());
            course.orElseThrow(() -> new NoSuchElementException("invalid course id "));

            enrollment.setCourse(course.get());
            enrollment.setJoin_status(RequestSet.waiting);
            enrollmentRepo.save(enrollment);
            return "your request is sent you will recieve code to join course depending upon Instructor Accept  ";
        } else if (e.get().getJoin_status() == RequestSet.Pending) {
            return "your Already requeste to join this course";
        } else if (e.get().getJoin_status() == RequestSet.Accepted) {
            return "you are Already  Joined ";
        } else {
            return "something wrong  join_status must be 0 or 1 or 2";
        }

    }


    public int Generate_and_sendCode(String mailAddresse, String message, String Subject) {

        Random random = new Random();
        int randomcode = random.nextInt(10000);

        // send mail
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailAddresse);
        mailMessage.setSubject(Subject);
        mailMessage.setFrom("omar394ahmed@gmail.com");
        mailMessage.setText(message + randomcode);
        verificationMailService.sendEmail(mailMessage);
        return randomcode;
    }

    public String confirmJoinCourse(int courseid, Long userid, int code) {

        Optional<Enrollment> enrollment = enrollmentRepo.findById(new St_Co_CompositeKey(userid, courseid));
        enrollment.orElseThrow(() -> new NoSuchElementException("invalid user id or course id "));
        if (enrollment.get().getJoin_code() == code) {
            enrollment.get().setJoin_status(RequestSet.Accepted);
            enrollmentRepo.save(enrollment.get());
            return "congrats you enrolled successfully ";
        } else {
            return "invalid code ";
        }
    }


    public List<Container> getstudentCourses(Long studentId) throws ExecutionException, InterruptedException {
        List<CourseDTO> firebaseCourses = firebaseService.getCourses();
        List<Container> mysqlCourses = repo.getStudentCourses(RequestSet.waiting , RequestSet.Pending, RequestSet.Accepted, studentId);
        List<Container> updated = new ArrayList<>();
        firebaseCourses.stream().forEach(courseDTO -> {
            mysqlCourses.stream().forEach(container -> {
                if (courseDTO.getId() == container.getCourse().getId()) {
                    BeanUtils.copyProperties(courseDTO, container.getCourse());
                    updated.add(container);
                    System.out.println("matching : mysql size  " + mysqlCourses.size() + " firebase size  : " + firebaseCourses.size());
                }
            });
        });

        return updated;

    }

    public List<CourseModel> getCoursesCreated(String token) throws ExecutionException, InterruptedException {
        List<CourseDTO> firbaseCourses = firebaseService.getCourses();
        List<CourseModel> mysqlCourses = repo.findAllByInstructorToken(token);
        List<CourseModel> updated = new ArrayList<>();

        firbaseCourses.stream().forEach(courseDTO -> {
            mysqlCourses.stream().forEach(courseModel -> {
                if (courseDTO.getId() == courseModel.getId()) {
                    BeanUtils.copyProperties(courseDTO, courseModel);
                    updated.add(courseModel);
                    //System.out.println("matching : mysql size  " + mysqlCourses.size() +  " firebase size  : " + firebaseCourses.size()  );
                }
            });
        });

        return updated;

    }

    public String deleteCourse(int courseid, String token) throws ExecutionException, InterruptedException {
        Optional<CourseModel> course = repo.findById(courseid);
        course.orElseThrow(() -> new NoSuchElementException("course not found"));
        repo.deleteByIdAndInstructorToken(courseid, token);
        String instructor = course.get().getInstructor().getUsername();
        String cousreName = course.get().getName();
        firebaseService.deleteCourse(instructor, cousreName);
        return "Deleted";
    }


//    public List<String> showWatingList(RequestsContainer requestList) {
//
//    }
}
