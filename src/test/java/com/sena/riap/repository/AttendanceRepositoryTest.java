package com.sena.riap.repository;

import com.sena.riap.entities.Attendance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    private Attendance attendance;

    @BeforeEach
    void setup(){
        attendance = Attendance.builder()
                .idAttendance(1L)
                .idEvent(2L)
                .idUser(3L)
                .attendanceTime(LocalDateTime.now())
                .build();
    }

    @Test
    void testSaveAttendance(){
        Attendance attendance1 = Attendance.builder()
                .idAttendance(1L)
                .idEvent(2L)
                .idUser(3L)
                .attendanceTime(LocalDateTime.now())
                .build();

        Attendance attendanceSave = attendanceRepository.save(attendance1);

        assertThat(attendance1).isNotNull();

    }


    @Test
    void testGetAttendanceById(){
        attendanceRepository.save(attendance);

        Attendance attendanceSave = attendanceRepository.findById(attendance.getIdAttendance()).get();

        assertThat(attendanceSave).isNotNull();

    }

    @Test
    void testDeleteAttendance() {
        // Crear una instancia de Attendance
        attendanceRepository.save(attendance);

        // Guardar la asistencia en la base de datos
        attendanceRepository.deleteById(attendance.getIdAttendance());
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(attendance.getIdAttendance());
        // Eliminar la asistencia de la base de datos
        assertThat(attendanceOptional).isEmpty();
    }

    @Test
    void testUpdateAttendance(){
        attendanceRepository.save(attendance);
        Attendance attendanceSave = attendanceRepository.findById(attendance.getIdAttendance()).get();
        attendanceSave.setIdAttendance(1L);
        attendanceSave.setIdEvent(2L);
        attendanceSave.setIdUser(3L);

        Attendance attendanceUpdate = attendanceRepository.save(attendanceSave);

        assertThat(attendanceUpdate.getIdAttendance()).isEqualTo(1L);
        assertThat(attendanceUpdate.getIdEvent()).isEqualTo(2L);
        assertThat(attendanceUpdate.getIdUser()).isEqualTo(3L);

    }

}