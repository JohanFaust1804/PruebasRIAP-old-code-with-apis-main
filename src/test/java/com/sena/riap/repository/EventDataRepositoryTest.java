package com.sena.riap.repository;

import com.sena.riap.entities.Course;
import com.sena.riap.entities.EventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EventDataRepositoryTest {
    @Autowired
    private EventDataRepository eventDataRepository;

    private EventData eventData;


    @BeforeEach
    void setup() {
        LocalDate fecha = LocalDate.of(2022, 4, 15);
        LocalTime star = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        eventData = EventData.builder()
                .idEvent(1L)
                .date(fecha)
                .startTime(star)
                .endTime(end)
                .location("Azalea")
                .objective("Desarrollo de eventos")
                .build();
    }

    @Test
    void testSaveEvent() {
        LocalDate fecha = LocalDate.of(2022, 4, 15);
        LocalTime star = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(11, 0);
        EventData eventData1 = EventData.builder()
                .idEvent(1L)
                .date(fecha)
                .startTime(star)
                .endTime(end)
                .objective("Desarrollo de eventos")
                .location("Azalea")
                .build();

        EventData eventSave = eventDataRepository.save(eventData1);

        assertThat(eventData1).isNotNull();

    }

    @Test
    void testGetCourseById() {
        eventDataRepository.save(eventData);

        EventData eventSave = eventDataRepository.findById(eventData.getIdEvent()).get();

        assertThat(eventSave).isNotNull();

    }

    @Test
    void testUpdateCourse() {
        eventDataRepository.save(eventData);
        EventData eventSave = eventDataRepository.findById(eventData.getIdEvent()).get();
        LocalDate fecha = LocalDate.of(2022, 5, 15);
        LocalTime start = LocalTime.of(11, 2);
        LocalTime end = LocalTime.of(12, 0);
        eventSave.setIdEvent(1L);
        eventSave.setLocation("Azalea");
        eventSave.setDate(fecha);
        eventSave.setStartTime(start);
        eventSave.setEndTime(end);
        eventSave.setObjective("Decoracion de espacios");

        EventData eventUpdate = eventDataRepository.save(eventSave);

        assertThat(eventUpdate.getIdEvent()).isEqualTo(1L);
        assertThat(eventUpdate.getLocation()).isEqualTo("Azalea");
        assertThat(eventUpdate.getDate()).isEqualTo(fecha);
        assertThat(eventUpdate.getStartTime()).isEqualTo(start);
        assertThat(eventUpdate.getEndTime()).isEqualTo(end);
        assertThat(eventUpdate.getObjective()).isEqualTo("Decoracion de espacios");
    }


    @Test
    void testDeleteCourse() {
        eventDataRepository.save(eventData);

        eventDataRepository.deleteById(eventData.getIdEvent());
        Optional<EventData> eventOptional = eventDataRepository.findById(eventData.getIdEvent());

        assertThat(eventOptional).isEmpty();
    }

}