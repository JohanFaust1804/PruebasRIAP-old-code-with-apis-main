package com.sena.riap.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sena.riap.entities.Attendance;
import com.sena.riap.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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

@WebMvcTest(controllers = AttendanceRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AttendanceRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceService attendanceService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void listAttendance()throws Exception{
        Attendance attendance1 = new Attendance();
        Attendance attendance2 = new Attendance();

        List<Attendance> attendanceList = Arrays.asList(attendance1, attendance2);

        when(attendanceService.getAttendance()).thenReturn(attendanceList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_attendance/list_attendance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idAttendance").value(attendance1.getIdAttendance()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].idAttendance").value(attendance2.getIdAttendance()));

        verify(attendanceService, times(1)).getAttendance();

    }

    @Test
    void getAttendance() throws Exception {
        Attendance attendance = new Attendance();
        attendance.setIdAttendance(1L);


        when(attendanceService.getAttendanceById(1L)).thenReturn(attendance);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_attendance/find/{id_attendance}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendance)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idAttendance").value(attendance.getIdAttendance()));

        verify(attendanceService, times(1)).getAttendanceById(1L);


    }

    @Test
    public void saveAttendance() throws Exception{
        Attendance attendance = new Attendance();
        attendance.setIdAttendance(1L); // Reemplaza 1L con el valor real
        attendance.setIdEvent(2L);
        when(attendanceService.saveAttendance(any())).thenReturn(attendance);

        mockMvc.perform(MockMvcRequestBuilders.post("/api_attendance/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendance)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idAttendance").value(1L));

        verify(attendanceService, times(1)).saveAttendance(any());
    }

    @Test
    void updateAttendance() throws Exception{
        Attendance attendance = new Attendance();
        long id = 1;
        attendance.setIdAttendance(id);

        when(attendanceService.updateAttendance(eq(id), any())).thenReturn(attendance);

        mockMvc.perform(MockMvcRequestBuilders.put("/api_attendance/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendance)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idAttendance").value(id));

        verify(attendanceService, times(1)).updateAttendance(eq(id), any());
    }

    @Test
    void deleteAttendance() throws Exception {
        Attendance attendance = new Attendance();
        long id = 1;
        attendance.setIdAttendance(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api_attendance/delete/{id_attendance}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(attendanceService, times(1)).deleteAttendance(id);
    }
}