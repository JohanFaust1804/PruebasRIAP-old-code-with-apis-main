package com.sena.riap.repository;

import com.sena.riap.entities.Attendance;
import com.sena.riap.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    void setup(){
        course = Course.builder()
                .idCourse(1L)
                .idProgram(2L)
                .number(3)
                .build();
    }

    @Test
    void testSaveCourse(){
        Course course1 = Course.builder()
                .idCourse(1L)
                .idProgram(2L)
                .number(3)
                .build();

        Course courseSave = courseRepository.save(course1);

        assertThat(course1).isNotNull();

    }


    @Test
    void testGetCourseById(){
        courseRepository.save(course);

        Course courseSave = courseRepository.findById(course.getIdCourse()).get();

        assertThat(courseSave).isNotNull();

    }

    @Test
    void testUpdateCourse(){
        courseRepository.save(course);
        Course courseSave = courseRepository.findById(course.getIdCourse()).get();
        courseSave.setIdCourse(1L);
        courseSave.setIdProgram(2L);
        courseSave.setNumber(3);

        Course courseUpdate =courseRepository.save(courseSave);

        assertThat(courseUpdate.getIdCourse()).isEqualTo(1L);
        assertThat(courseUpdate.getIdProgram()).isEqualTo(2L);
        assertThat(courseUpdate.getNumber()).isEqualTo(3);

    }

    @Test
    void testDeleteCourse() {
        courseRepository.save(course);

        courseRepository.deleteById(course.getIdCourse());
        Optional<Course> courseOptional = courseRepository.findById(course.getIdCourse());

        assertThat(courseOptional).isEmpty();
    }
}