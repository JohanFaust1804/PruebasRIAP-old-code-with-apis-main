package com.sena.riap.service.impl;

import com.sena.riap.entities.UserCourse;
import com.sena.riap.entities.UserData;
import com.sena.riap.repository.UserCourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserCourseServiceImplTest {

    @Mock
    private UserCourseRepository userCourseRepository;

    @InjectMocks
    private UserCourseServiceImpl userCourseService;

    private UserCourse userCourse;

    @BeforeEach
    void setup() {
        userCourse = UserCourse.builder()
                .idCourse(3L)
                .idUser(4L)
                .idUserCourse(2L)
                .build();
    }

    @Test
    void getUserCourse() {
        given(userCourseRepository.findById(2L)).willReturn(Optional.of(userCourse));


        UserCourse userCourseRetornado = userCourseService.getUserCourseById(userCourse.getIdUserCourse());


        assertThat(userCourseRetornado).isNotNull();
        assertThat(userCourseRetornado.getIdUserCourse()).isEqualTo(2L);
        assertThat(userCourseRetornado.getIdUser()).isEqualTo(4L);
        assertThat(userCourseRetornado.getIdCourse()).isEqualTo(3L);
    }

    @Test
    void saveUserCourse() {
        given(userCourseRepository.save(userCourse)).willReturn(userCourse);
        UserCourse saveUserCourse = userCourseService.saveUserCourse(userCourse);


        assertThat(saveUserCourse).isNotNull();
        assertThat(saveUserCourse).isEqualTo(userCourse);


        verify(userCourseRepository, times(1)).save(userCourse);
    }

    @Test
    void getUserCourseById() {
        given(userCourseRepository.findById(2L)).willReturn(Optional.of(userCourse));


        UserCourse userSave = userCourseService.getUserCourseById(userCourse.getIdUserCourse());


        assertThat(userSave).isNotNull();
    }

    @Test
    void updateUserCourse() {
        Long idUserCourse = 1L;
        UserCourse userCourse = UserCourse.builder()
                .idUser(1L)
                .idCourse(2L)

                .build();
        given(userCourseRepository.findById(idUserCourse)).willReturn(Optional.of(userCourse));
        given(userCourseRepository.save(userCourse)).willReturn(userCourse);




        UserCourse userUpdate = userCourseService.updateUserCourse(idUserCourse, userCourse);


        assertThat(userUpdate).isNotNull();
        assertThat(userUpdate.getIdUser()).isEqualTo(1L);



        verify(userCourseRepository, times(1)).save(userCourse);
    }

    @Test
    void deleteUserCourse() {
        long id = 3L;
        willDoNothing().given(userCourseRepository).deleteById(id);
        userCourseService.deleteUserCourse(id);
        verify(userCourseRepository, times(1)).deleteById(id);
    }

}