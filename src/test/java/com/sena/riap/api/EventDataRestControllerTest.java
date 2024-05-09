package com.sena.riap.api;


import com.sena.riap.entities.Course;
import com.sena.riap.entities.EventData;
import com.sena.riap.service.EventDataService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = EventDataRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class EventDataRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventDataService eventDataService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listEventData() throws Exception{
        EventData eventData1 = new EventData();
        EventData eventData2 = new EventData();

        List<EventData> eventDataList = Arrays.asList(eventData1, eventData2);

        when(eventDataService.getEventData()).thenReturn(eventDataList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_event_data/list_event_data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idEvent").value(eventData1.getIdEvent()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].idEvent").value(eventData2.getIdEvent()));

        verify(eventDataService, times(1)).getEventData();

    }

    @Test
    void getEventData() throws Exception{
        EventData eventData1 = new EventData();
        eventData1.setIdEvent(1L);

        when(eventDataService.getEventDataById(1L)).thenReturn(eventData1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api_event_data/find/{id_event}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventData1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idEvent").value(eventData1.getIdEvent()));
        verify(eventDataService, times(1)).getEventDataById(1L);


    }

    @Test
    void saveEventData() throws Exception {
        EventData eventData1 = new EventData();
        eventData1.setIdEvent(1L);
        eventData1.setLocation("Lomas turbas");

        when(eventDataService.saveEventData(any())).thenReturn(eventData1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api_event_data/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventData1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idEvent").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Lomas turbas"));
        verify(eventDataService, times(1)).saveEventData(any());

    }

    @Test
    void updateEventData() throws Exception {
        EventData eventData1 = new EventData();
        long id = 2;
        eventData1.setIdEvent(id);

        when(eventDataService.updateEventData(eq(id), any())).thenReturn(eventData1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api_event_data/update/"+ id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventData1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idEvent").value(id));
        verify(eventDataService, times(1)).updateEventData(eq(id), any());




    }

    @Test
    void deleteEventData() throws Exception {
        EventData eventData1 = new EventData();
        long id = 2;
        eventData1.setIdEvent(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api_event_data/delete/{id_event}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(eventDataService, times(1)).deleteEventData(id);

    }
}