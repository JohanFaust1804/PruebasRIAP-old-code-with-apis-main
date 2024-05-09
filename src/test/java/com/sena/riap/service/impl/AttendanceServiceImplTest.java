package com.sena.riap.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;

import com.sena.riap.entities.Attendance;
import com.sena.riap.repository.AttendanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceImplTest { // fino

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    private Attendance attendance;

    @BeforeEach
    void setup (){
        LocalDateTime  currentTime = LocalDateTime.now();
        attendance = Attendance.builder()
                .idAttendance(4L)
                .idEvent(6L)
                .idUser(9L)
                .attendanceTime(currentTime)
                .build();
    }

    @Test
    void saveAttendance() {
        given(attendanceRepository.save(attendance)).willReturn(attendance);

        Attendance attendance1 = attendanceService.saveAttendance(attendance);

        assertThat(attendance1).isNotNull();
        //System.out.println(attendance1.getIdAttendance() + " event " +  attendance1.getIdEvent() + " user " +attendance1.getIdUser());
    }

    @Test
    void getAttendanceById() {
        given(attendanceRepository.findById(4L)).willReturn(Optional.of(attendance));

        Attendance attendance1 = attendanceService.getAttendanceById(attendance.getIdAttendance());

        assertThat(attendance1).isNotNull();
        assertThat(attendance1.getIdAttendance()).isEqualTo(4L);
    }

    @Test
    void updateAttendance() {
        Long idAttendance = 4L;
        LocalDateTime  currentTime = LocalDateTime.now();
        attendance = Attendance.builder()
                .idAttendance(6L)
                .idEvent(6L)
                .idUser(9L)
                .attendanceTime(currentTime)
                .build();
        given(attendanceRepository.findById(idAttendance)).willReturn(Optional.of(attendance));
        given(attendanceRepository.save(attendance)).willReturn(attendance);

        Attendance attendance1 = attendanceService.updateAttendance(idAttendance, attendance);

        assertThat(attendance1).isNotNull();
        assertThat(attendance1.getIdAttendance()).isEqualTo(6L);

        verify(attendanceRepository, times(1)).save(attendance);
    }

    @Test
    void deleteAttendance() {
        Long id = 5L;
        willDoNothing().given(attendanceRepository).deleteById(id);
        attendanceService.deleteAttendance(id);
        verify(attendanceRepository, times(1)).deleteById(id);
    }
}