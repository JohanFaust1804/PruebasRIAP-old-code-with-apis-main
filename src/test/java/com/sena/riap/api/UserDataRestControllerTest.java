package com.sena.riap.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sena.riap.entities.Program;
import com.sena.riap.entities.UserData;
import com.sena.riap.service.UserDataService;
import org.junit.jupiter.api.Test;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import java.time.LocalDateTime;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserDataRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserDataRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserDataService userDataService;

    @Test
    public void listUserData() throws Exception {
        UserData userData1 = new UserData(1L, "300456", 12, "Joel Emilio Rodriguez", "cagon@45gmail.com", "47382", "admin", "321", "joel.jpg");
        UserData userData2 = new UserData(2L, "300456", 12, "Joel Emilio Rodriguez", "cagon@45gmail.com", "47382", "admin", "321", "joel.jpg");

        List<UserData> userDataList = Arrays.asList(userData1, userData2);
        when(userDataService.getUserData()).thenReturn(userDataList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_user/list_user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idUser").value(userData1.getIdUser()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].idUser").value(userData2.getIdUser()));
        verify(userDataService, times(1)).getUserData();
    }

    @Test
    void saveUserData() throws Exception {
        UserData userData1 = new UserData();
        long id = 1;
        userData1.setIdUser(id);

        when(userDataService.saveUserData(any())).thenReturn(userData1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api_user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userData1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idUser").value(1L));
        verify(userDataService, times(1)).saveUserData(any());

    }

    @Test
    void updateUserData() throws Exception {
        UserData userData1 = new UserData();
        long id = 1;
        userData1.setIdUser(id);
        when(userDataService.updateUserData(eq(id), any())).thenReturn(userData1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api_user/update/"+ id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userData1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idUser").value(id));
        verify(userDataService, times(1)).updateUserData(eq(id), any());

    }

    @Test
    void deleteUserData() throws Exception {
        UserData userData1 = new UserData();
        long id = 1;
        userData1.setIdUser(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api_user/delete/{id_user}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(userDataService, times(1)).deleteUserData(id);
    }
}