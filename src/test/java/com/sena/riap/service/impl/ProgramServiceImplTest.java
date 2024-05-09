package com.sena.riap.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import com.sena.riap.entities.Program;
import com.sena.riap.repository.ProgramRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProgramServiceImplTest {

    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private ProgramServiceImpl programService;

    private Program program;

    @BeforeEach
    void setup(){
        program = Program.builder()
                .idProgram(1L)
                .name("Programation")
                .build();
    }


    @Test
    void getProgram() {
        given(programRepository.findById(1L)).willReturn(Optional.of(program));

        Program program1 = programService.getProgramById(program.getIdProgram());

        assertThat(program1).isNotNull();
        assertThat(program1.getIdProgram()).isEqualTo(1L);
    }

    @Test
    void saveProgram() {
        given(programRepository.save(program)).willReturn(program);

        Program programSave = programService.saveProgram(program);

        assertThat(programSave).isNotNull();

    }

    @Test
    void getProgramById() {
        given(programRepository.findById(1L)).willReturn(Optional.of(program));

        Program program1 = programService.getProgramById(program.getIdProgram());

        assertThat(program1).isNotNull();
        assertThat(program1.getIdProgram()).isEqualTo(1L);
    }

    @Test
    void updateProgram() {
        Long idProgram = 1L;
        program = Program.builder()
                .idProgram(2L)
                .name("Programation")
                .build();
        given(programRepository.findById(idProgram)).willReturn(Optional.of(program));
        given(programRepository.save(program)).willReturn(program);



        //when
        Program programUpdate = programService.updateProgram(idProgram, program);

        // then
        assertThat(programUpdate).isNotNull();
        assertThat(programUpdate.getIdProgram()).isEqualTo(2L);


        // verify that save method was called once with the modified object
        verify(programRepository, times(1)).save(program);

    }

    @Test
    void deleteProgram() {
        Long id = 1L;
        willDoNothing().given(programRepository).deleteById(id);
        programService.deleteProgram(id);
        verify(programRepository, times(1)).deleteById(id);
    }
}