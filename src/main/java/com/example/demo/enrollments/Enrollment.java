package com.example.demo.enrollments;

import com.example.demo.courses.CourseDTO;
import com.example.demo.courses.CourseModel;
import com.example.demo.users.User;
import com.example.demo.users.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dom4j.QName;
import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;


@SuppressWarnings("ALL")
@Entity
@SqlResultSetMapping(
        name = "studentRequest",
        classes = {
                @ConstructorResult(
                        targetClass = UserDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "username", type = String.class) ,
                                @ColumnResult(name="join_status" , type =Integer.class  )
                        }
                )
        }
)

@NamedNativeQuery(name = "Enrollment.getcourseStudents",
        query = "SELECT id , name , username  , join_status FROM enrollment e INNER JOIN user ON student_id = id \n" +
                "WHERE e.course_id = :courseid and  e.join_status = :status",
        resultSetMapping = "studentRequest")

public class Enrollment {
    @EmbeddedId
    private St_Co_CompositeKey key;

    private int student_Rate;
    @JsonIgnore
    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "smallint")
    private RequestSet join_status;
    @JsonIgnore
    private int Join_code;

    @JsonIgnore
    @ManyToOne
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    private CourseModel course;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    private User user;

    public Enrollment() {
    }

    public St_Co_CompositeKey getKey() {
        return key;
    }

    public void setKey(St_Co_CompositeKey key) {
        this.key = key;
    }
    @JsonProperty("rate")
    public int getStudent_Rate() {
        return student_Rate;
    }
    @JsonIgnore
    public void setStudent_Rate(int student_Rate) {
        this.student_Rate = student_Rate;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RequestSet getJoin_status() {
        return join_status;
    }

    public void setJoin_status(RequestSet join_status) {
        this.join_status = join_status;
    }
   @JsonIgnore
    public int getJoin_code() {
        return Join_code;
    }

    public void setJoin_code(int join_code) {
        Join_code = join_code;
    }
}
