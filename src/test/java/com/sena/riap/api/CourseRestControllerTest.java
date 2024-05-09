package com.sena.riap.api;

import com.sena.riap.entities.Course;
import com.sena.riap.service.CourseService;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = CourseRestController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listCourse() throws Exception{
        Course course1 = new Course();
        Course course2 = new Course();

        List<Course> courseList = Arrays.asList(course1, course2);

        when(courseService.getCourse()).thenReturn(courseList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_course/list_course")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idCourse").value(course1.getIdCourse()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].idCourse").value(course2.getIdCourse()));

        verify(courseService, times(1)).getCourse();

    }

    @Test
    void getCourse() throws Exception{
        Course course1 = new Course();
        course1.setIdCourse(1L);

        when(courseService.getCourseById(1L)).thenReturn(course1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_course/find/{id_course}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCourse").value(course1.getIdCourse()));

        verify(courseService, times(1)).getCourseById(1L);

    }

    @Test
    void saveCourse() throws  Exception {
        Course course1 = new Course();
        course1.setIdCourse(1L);
        course1.setNumber(2);

        when(courseService.saveCourse(any())).thenReturn(course1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api_course/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCourse").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(2));
        verify(courseService, times(1)).saveCourse(any());



    }

    @Test
    void updateCourse() throws Exception{
        Course course1 =  new Course();
        long id = 1;
        course1.setIdCourse(id);

        when(courseService.updateCourse(eq(id), any())).thenReturn(course1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api_course/update/"+ id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(course1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCourse").value(id));
        verify(courseService, times(1)).updateCourse(eq(id), any());

    }

    @Test
    void deleteCourse() throws Exception {
        Course course1 =  new Course();
        long id = 1;
        course1.setIdCourse(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api_course/delete/{id_course}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(courseService, times(1)).deleteCourse(id);

    }
}