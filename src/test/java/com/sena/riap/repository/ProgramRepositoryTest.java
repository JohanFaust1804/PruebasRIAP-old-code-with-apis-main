package com.sena.riap.repository;

import com.sena.riap.entities.EventData;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import com.sena.riap.entities.Program;
import com.sena.riap.repository.ProgramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@DataJpaTest
class ProgramRepositoryTest {


    @Autowired
    private ProgramRepository programRepository;

    private Program program;

    @BeforeEach
    void setup() {
        program = Program.builder()
                .idProgram(1L)
                .name("Desarrolo de software")
                .build();
    }

    @Test
    void testSaveProgram() {
        Program program1 = Program.builder()
                .idProgram(1L)
                .name("Desarrolo de software")
                .build();

        Program programSave = programRepository.save(program1);

        assertThat(program1).isNotNull();

    }

    @Test
    void testGetCourseById() {
        programRepository.save(program);

        Program programSave = programRepository.findById(program.getIdProgram()).get();

        assertThat(programSave).isNotNull();

    }

    @Test
    void testUpdateCourse() {
        programRepository.save(program);

        Program programSave = programRepository.findById(program.getIdProgram()).get();
        programSave.setIdProgram(1L);
        programSave.setName("Analisis de datos");
        Program programUpdate = programRepository.save(programSave);

        assertThat(programUpdate.getIdProgram()).isEqualTo(1L);
        assertThat(programUpdate.getName()).isEqualTo("Analisis de datos");

    }


    @Test
    void testDeleteCourse() {
        programRepository.save(program);

        programRepository.deleteById(program.getIdProgram());
        Optional<Program> programOptional = programRepository.findById(program.getIdProgram());

        assertThat(programOptional).isEmpty();
    }
}
