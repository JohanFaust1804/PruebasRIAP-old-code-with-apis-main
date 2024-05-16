package com.sena.riap.repository;
import com.sena.riap.entities.UserCourse;
import com.sena.riap.entities.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserDataRepositoryTest {

    @Autowired(required = false)
    private UserDataRepository userDataRepository;
    private UserData userData;

    @BeforeEach
    void setUp() {
        userData = UserData.builder()
                .document("1123214553")
                .password("insano12")
                .email("example@example.com")
                .profilePicture("profile.jpg")
                .phone("3213213211")
                .roleUser("admin")
                .nameUser("John Doe")
                .build();
    }
    @Test
    void testSaveUserData(){
        UserData userData1 = UserData.builder()
                .document("1123214553")
                .password("insano12")
                .email("example@example.com")
                .profilePicture("profile.jpg")
                .phone("3213213211")
                .roleUser("admin")
                .nameUser("John Doe")
                .build();

        UserData userDataSave = userDataRepository.save(userData1);

        assertThat(userDataSave).isNotNull();

    }
    @Test
    void testGetUserDataById(){
        userDataRepository.save(userData);

        UserData userDataSave = userDataRepository.findById(userData.getIdUser()).get();

        assertThat(userDataSave).isNotNull();
    }

    @Test
    void testUpdateUserData(){
        userDataRepository.save(userData);

        UserData userDataSave = userDataRepository.findById(userData.getIdUser()).get();
        userDataSave.setIdUser(1L);
        userDataSave.setNameUser("Joe");
        userDataSave.setDocument("10232212");
        userDataSave.setAge(23);
        userDataSave.setRoleUser("Admin");
        userDataSave.setEmail("example@example.com");
        userDataSave.setPassword("ramirez13");
        userDataSave.setPhone("3212149144");
        userDataSave.setProfilePicture("profile.jpg");
        UserData userDataUpdate = userDataRepository.save(userDataSave);
        assertThat(userDataUpdate.getIdUser()).isEqualTo(1L);
        assertThat(userDataUpdate.getNameUser()).isEqualTo("Joel");
        assertThat(userDataUpdate.getDocument()).isEqualTo("10232212");
        assertThat(userDataUpdate.getRoleUser()).isEqualTo("Admin");
        assertThat(userDataUpdate.getEmail()).isEqualTo("example@example.com");
        assertThat(userDataUpdate.getPassword()).isEqualTo("ramirez13");
        assertThat(userDataUpdate.getPhone()).isEqualTo("3212149144");
        assertThat(userDataUpdate.getProfilePicture()).isEqualTo("profile.jpg");

    }

    @Test
    void testDeleteData(){
        UserData userData1 = new UserData();

        userDataRepository.save(userData);

        userDataRepository.deleteById(userData.getIdUser());


        assertThat(userData1).isNotNull();
    }

}