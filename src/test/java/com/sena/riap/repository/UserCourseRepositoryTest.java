package com.sena.riap.repository;

import com.sena.riap.entities.Course;
import com.sena.riap.entities.Program;
import com.sena.riap.entities.UserCourse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserCourseRepositoryTest {

    @Autowired
    private UserCourseRepository userCourseRepository;

    private UserCourse userCourse;

    @BeforeEach
    void setup() {
        userCourse = UserCourse.builder()
                .idUserCourse(1L)
                .idUser(2L)
                .idCourse(3L)
                .build();
    }

    @Test
    void testSaveProgram() {
        UserCourse userCourse1 = UserCourse.builder()
                .idUserCourse(1L)
                .idUser(2L)
                .idCourse(3L)
                .build();

        UserCourse userCourseSave = userCourseRepository.save(userCourse1);

        assertThat(userCourse1).isNotNull();

    }

    @Test
    void testGetCourseById() {
        userCourseRepository.save(userCourse);

        UserCourse userCourseSave = userCourseRepository.findById(userCourse.getIdUserCourse()).get();

        assertThat(userCourseSave).isNotNull();

    }

    @Test
    void testUpdateCourse() {
        userCourseRepository.save(userCourse);

        UserCourse userCourseSave = userCourseRepository.findById(userCourse.getIdUserCourse()).get();
        userCourseSave.setIdUserCourse(1L);
        userCourseSave.setIdCourse(2L);
        userCourseSave.setIdUser(3L);
        UserCourse userCourseUpdate = userCourseRepository.save(userCourseSave);

        assertThat(userCourseUpdate.getIdUserCourse()).isEqualTo(1L);
        assertThat(userCourseUpdate.getIdCourse()).isEqualTo(2L);
        assertThat(userCourseUpdate.getIdUser()).isEqualTo(3L);

    }


    @Test
    void testDeleteCourse() {
        userCourseRepository.save(userCourse);

        userCourseRepository.deleteById(userCourse.getIdUserCourse());
        Optional<UserCourse> userCourseOptional = userCourseRepository.findById(userCourse.getIdUserCourse());

        assertThat(userCourseOptional).isEmpty();
    }
}