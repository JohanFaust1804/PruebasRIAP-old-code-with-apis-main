package com.sena.riap.service.impl;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;
import com.sena.riap.entities.UserData;
import com.sena.riap.repository.UserDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;



@ExtendWith(MockitoExtension.class)
class UserDataServiceImplTest {

    @Mock
    private  UserDataRepository userDataRepository;

    @InjectMocks
    private UserDataServiceImpl userDataService;

    private UserData userData;

    @BeforeEach
    void setup(){
        userData = UserData.builder()
                .idUser(1L)
                .document("23223")
                .age(20)
                .nameUser("Oscar")
                .email("supapa23@")
                .phone("23233")
                .roleUser("gey")
                .password("123")
                .profilePicture("yei")
                .build();
    }

    @Test
    void saveUserData() {
        //given
        given(userDataRepository.save(userData)).willReturn(userData);
        UserData savedUserData = userDataService.saveUserData(userData);

        // then
        assertThat(savedUserData).isNotNull();
        assertThat(savedUserData).isEqualTo(userData);

        // verify that save method was called once
        verify(userDataRepository, times(1)).save(userData);

    }

    @Test
    void updateUserData() {
        //given
        Long idUser = 1L;
        UserData userData = UserData.builder()
                .idUser(1L)
                .document("23223")
                .age(20)
                .nameUser("cachon")  // Updated name
                .email("supapa23@")
                .phone("23233")
                .roleUser("gey")
                .password("123")
                .profilePicture("yei")
                .build();
        given(userDataRepository.findById(idUser)).willReturn(Optional.of(userData));
        given(userDataRepository.save(userData)).willReturn(userData);
        userData.setNameUser("cachon");

        //when
        UserData userUpdate = userDataService.updateUserData(idUser, userData);

        // then
        assertThat(userUpdate).isNotNull();
        assertThat(userUpdate.getIdUser()).isEqualTo(1L);
        assertThat(userUpdate.getNameUser()).isEqualTo("cachon");

        // verify that save method was called once with the modified object
        verify(userDataRepository, times(1)).save(userData);
    }

    @Test
    void deleteUserData() {
        //given
        long id = 3L;
        willDoNothing().given(userDataRepository).deleteById(id);
        //when
        userDataService.deleteUserData(id);
        //then
        verify(userDataRepository,times(1)).deleteById(id);
    }
}