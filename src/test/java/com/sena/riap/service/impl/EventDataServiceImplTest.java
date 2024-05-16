package com.sena.riap.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;

import com.sena.riap.entities.EventData;
import com.sena.riap.repository.EventDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventDataServiceImplTest {

    @Mock
    private EventDataRepository eventDataRepository;

    @InjectMocks
    private EventDataServiceImpl eventDataService;

    private EventData eventData;

    @BeforeEach
    void setup(){

        LocalDate dateEvent = LocalDate.now().plusDays(1); // Obtener la fecha de mañana como ejemplo
        LocalTime startEvent = LocalTime.of(9, 0); // Hora de inicio a las 9:00 AM como ejemplo
        LocalTime endEvent = LocalTime.of(12, 0);

        eventData = EventData.builder()
                .idEvent(2L)
                .objective("Welcome new prom")
                .date(dateEvent)
                .endTime(startEvent)
                .startTime(endEvent)
                .location("Portsman")
                .build();
    }

    @Test
    void getEventData() {
        given(eventDataRepository.findById(2L)).willReturn(Optional.of(eventData));

        EventData eventData1 = eventDataService.getEventDataById(eventData.getIdEvent());

        assertThat(eventData1).isNotNull();
        assertThat(eventData1.getIdEvent()).isEqualTo(2l);
    }



    @Test
    void saveEventData() {
        given(eventDataRepository.save(eventData)).willReturn(eventData);

        EventData eventData1 = eventDataService.saveEventData(eventData);

        assertThat(eventData1).isNotNull();


    }

    @Test
    void getEventDataById() {

        given(eventDataRepository.findById(2L)).willReturn(Optional.of(eventData));

        EventData eventData1 = eventDataService.getEventDataById(eventData.getIdEvent());

        assertThat(eventData1).isNotNull();
        assertThat(eventData1.getIdEvent()).isEqualTo(2l);

    }

    @Test
    void updateEventData() {
        Long idEvent = 4L;

        LocalDate dateEvent = LocalDate.now().plusDays(1); // Obtener la fecha de mañana como ejemplo
        LocalTime startEvent = LocalTime.of(9, 0); // Hora de inicio a las 9:00 AM como ejemplo
        LocalTime endEvent = LocalTime.of(12, 0);

        eventData = EventData.builder()
                    .idEvent(2L)
                    .objective("Welcome new prom")
                    .date(dateEvent)
                    .endTime(startEvent)
                    .startTime(endEvent)
                    .location("Portsman")
                    .build();

        given(eventDataRepository.findById(idEvent)).willReturn(Optional.of(eventData));
        given(eventDataRepository.save(eventData)).willReturn(eventData);

        EventData eventData1 = eventDataService.updateEventData(idEvent, eventData);

        assertThat(eventData1).isNotNull();
        assertThat(eventData1.getIdEvent()).isEqualTo(2L);

        verify(eventDataRepository, times(1)).save(eventData);
    }


    @Test
    void deleteEventData() {
        Long id = 3L;
        willDoNothing().given(eventDataRepository).deleteById(id);
        eventDataService.deleteEventData(id);
        verify(eventDataRepository, times(1)).deleteById(id);
    }
}

