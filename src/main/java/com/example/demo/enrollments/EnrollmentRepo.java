package com.example.demo.enrollments;

import com.example.demo.courses.CourseDTO;
import com.example.demo.users.UserDTO;
import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, St_Co_CompositeKey> {
    /*@Query(name="Enrollment.getUserCourses" , nativeQuery = true)
    List<CourseDTO> getUserCourses(@Param("student_id") Long id  , @Param("status1") int pending, @Param("status2") int accept);
*/


    public List<Enrollment> findAll(Sort sort);

    @Query(name= "Enrollment.getcourseStudents" , nativeQuery = true )
    public List<UserDTO> getcourseStudents(@Param("courseid") int courseid , @Param("status") int  status);


}
