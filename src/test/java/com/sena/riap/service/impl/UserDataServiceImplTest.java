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
                .email("example23@")
                .phone("23233")
                .roleUser("gey")
                .password("123")
                .profilePicture("yei")
                .build();
    }

    @Test
    void saveUserData() {

        given(userDataRepository.save(userData)).willReturn(userData);
        UserData savedUserData = userDataService.saveUserData(userData);


        assertThat(savedUserData).isNotNull();
        assertThat(savedUserData).isEqualTo(userData);


        verify(userDataRepository, times(1)).save(userData);

    }

    @Test
    void updateUserData() {

        Long idUser = 1L;
        UserData userData = UserData.builder()
                .idUser(1L)
                .document("23223")
                .age(20)
                .nameUser("oscar")  // Updated name
                .email("example23@")
                .phone("23233")
                .roleUser("Admin")
                .password("123")
                .profilePicture("profile.jpg")
                .build();
        given(userDataRepository.findById(idUser)).willReturn(Optional.of(userData));
        given(userDataRepository.save(userData)).willReturn(userData);
        userData.setNameUser("oscar");


        UserData userUpdate = userDataService.updateUserData(idUser, userData);


        assertThat(userUpdate).isNotNull();
        assertThat(userUpdate.getIdUser()).isEqualTo(1L);
        assertThat(userUpdate.getNameUser()).isEqualTo("oscar");


        verify(userDataRepository, times(1)).save(userData);
    }

    @Test
    void deleteUserData() {

        long id = 3L;
        willDoNothing().given(userDataRepository).deleteById(id);

        userDataService.deleteUserData(id);

        verify(userDataRepository,times(1)).deleteById(id);
    }
}