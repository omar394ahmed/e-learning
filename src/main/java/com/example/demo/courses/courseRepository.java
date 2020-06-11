package com.example.demo.courses;

import com.example.demo.enrollments.RequestSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface courseRepository extends JpaRepository<CourseModel, Integer> {
    Optional<CourseModel> findByTagAndName(String tag, String name);

    Optional<CourseModel> findByIdAndInstructorToken( int id , String token);

    List<CourseModel> findAllByInstructorToken(String token);

    void  deleteByIdAndInstructorToken(int id , String token);

    @Query(name ="CourseModel.getStudentCourses")
    List<Container>getStudentCourses(@Param("status1") RequestSet status1 ,@Param("status2") RequestSet status2 , @Param("status3") RequestSet status3 ,@Param("id") Long id );
    @Query(name="CourseModel.getAllCourses")
    Set<CourseModel> getAllCourses(@Param("id") long id);

}
