package com.sena.riap.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;

import com.sena.riap.entities.Course;
import com.sena.riap.entities.EventData;
import com.sena.riap.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private  CourseServiceImpl courseService;

    private Course course;

    @BeforeEach
    void setup(){
        course = Course.builder()
                .idCourse(2L)
                .idProgram(3L)
                .number(233)
                .build();
    }

    @Test
    void getCourse() {
        given(courseRepository.findById(2L)).willReturn(Optional.of(course));

        Course course1 = courseService.getCourseById(course.getIdCourse());

        assertThat(course1).isNotNull();
        assertThat(course1.getIdCourse()).isEqualTo(2L);
    }

    @Test
    void saveCourse() {
        given(courseRepository.save(course)).willReturn(course);

        Course course1 = courseService.saveCourse(course);

        assertThat(course1).isNotNull();
    }

    @Test
    void getCourseById() {
        given(courseRepository.findById(2L)).willReturn(Optional.of(course));

        Course course1 = courseService.getCourseById(course.getIdCourse());

        assertThat(course1).isNotNull();
        assertThat(course1.getIdCourse()).isEqualTo(2L);
    }

    @Test
    void updateCourse() {
        Long idCourse = 5L;

        course = Course.builder()
                .idCourse(3L)
                .number(23)
                .idProgram(1L)
                .build();
        given(courseRepository.findById(idCourse)).willReturn(Optional.of(course));
        given(courseRepository.save(course)).willReturn(course);

        Course courseUpdate = courseService.updateCourse(idCourse, course);

        assertThat(courseUpdate).isNotNull();
        assertThat(courseUpdate.getIdCourse()).isEqualTo(3L);

    }

    @Test
    void deleteCourse() {
        Long id = 2L;
        willDoNothing().given(courseRepository).deleteById(id);
        courseService.deleteCourse(id);
        verify(courseRepository, times(1)).deleteById(id);
    }
}