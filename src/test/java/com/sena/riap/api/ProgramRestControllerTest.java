package com.sena.riap.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sena.riap.entities.Program;
import com.sena.riap.service.ProgramService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
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

@WebMvcTest(controllers = ProgramRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ProgramRestControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProgramService programService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void listProgram() throws Exception{
        Program program1 = new Program();
        Program program2 = new Program();

        List<Program> programArrayList = Arrays.asList(program1, program2);
        when(programService.getProgram()).thenReturn(programArrayList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_program/list_program")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idProgram").value(program1.getIdProgram()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].idProgram").value(program2.getIdProgram()));
        verify(programService, times(1)).getProgram();

    }

    @Test
    void getProgram() throws Exception {
        Program program1 = new Program();
        program1.setIdProgram(1L);

        when(programService.getProgramById(1L)).thenReturn(program1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api_program/find/{id_program}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(program1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idProgram").value(program1.getIdProgram()));
        verify(programService, times(1)).getProgramById(1L);


    }

    @Test
    void saveProgram() throws Exception {
        Program program1 = new Program();
        long id = 1;
        program1.setIdProgram(id);

        when(programService.saveProgram(any())).thenReturn(program1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api_program/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(program1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idProgram").value(1L));
        verify(programService, times(1)).saveProgram(any());


    }

    @Test
    void updateProgram() throws Exception {
        Program program1 = new Program();
        long id = 1;
        program1.setIdProgram(id);
        when(programService.updateProgram(eq(id), any())).thenReturn(program1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api_program/update/"+ id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(program1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idProgram").value(id));
        verify(programService, times(1)).updateProgram(eq(id), any());
    }

    @Test
    void deleteProgram() throws Exception{
        Program program1 = new Program();
        long id = 1;
        program1.setIdProgram(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api_program/delete/{id_user}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(programService, times(1)).deleteProgram(id);
    }
}