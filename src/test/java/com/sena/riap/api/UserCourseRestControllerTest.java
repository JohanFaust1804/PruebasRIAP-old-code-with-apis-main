package com.sena.riap.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sena.riap.entities.UserCourse;
import com.sena.riap.service.UserCourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.hamcrest.CoreMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
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

@WebMvcTest(controllers = UserCourseRestController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UserCourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserCourseService userCourseService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void listUserCourse() throws Exception {
        UserCourse userCourse1 = new UserCourse();
        UserCourse userCourse2 = new UserCourse();

        List<UserCourse> userCourseList = Arrays.asList(userCourse1, userCourse2);
        when(userCourseService.getUserCourse()).thenReturn(userCourseList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api_user_course/list_user_course")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].idUserCourse").value(userCourse1.getIdUserCourse()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[1].idUserCourse").value(userCourse2.getIdUserCourse()));
        verify(userCourseService, times(1)).getUserCourse();
    }

    @Test
    void getUserCourse() throws Exception {
        UserCourse userCourse1 = new UserCourse();
        userCourse1.setIdUserCourse(1L);

        when(userCourseService.getUserCourseById(1L)).thenReturn(userCourse1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_user_course/find/{id_user_course}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCourse1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idUserCourse").value(userCourse1.getIdUserCourse()));
        verify(userCourseService, times(1)).getUserCourseById(1L);



    }

    @Test
    void saveUserCourse() throws Exception {
        UserCourse userCourse1 = new UserCourse();
        long id = 1;
        userCourse1.setIdUserCourse(id);

        when(userCourseService.saveUserCourse(any())).thenReturn(userCourse1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api_user_course/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCourse1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idUserCourse").value(id));
        verify(userCourseService, times(1)).saveUserCourse(any());
    }

    @Test
    void updateUserCourse() throws Exception{
        UserCourse userCourse1 = new UserCourse();
        long id = 1;
        userCourse1.setIdUserCourse(id);

        when(userCourseService.updateUserCourse(eq(id), any())).thenReturn(userCourse1);

        mockMvc.perform(MockMvcRequestBuilders.put("/api_user_course/update/"+ id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCourse1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idUserCourse").value(id));
        verify(userCourseService, times(1)).updateUserCourse(eq(id), any());
    }

    @Test
    void deleteUserCourse() throws Exception {
        UserCourse userCourse1 = new UserCourse();
        long id = 1;
        userCourse1.setIdUserCourse(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api_user_course/delete/{id_user_course}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(userCourseService, times(1)).deleteUserCourse(id);


    }
}