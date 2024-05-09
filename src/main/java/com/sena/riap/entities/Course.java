package com.sena.riap.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Entity
@Table(name = "course")
@Builder
public class Course {

    @Id
    @Column(name = "id_course")
    private Long idCourse;

    @NotNull(message = "Number cannot be null")
    @Size(max = 10)
    @Column(name = "number_course")
    private Integer number;

    //  @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "id_program")
    private Long idProgram;

    public Course (){

    }

    public Course (Long idCourse, Integer number, Long idProgram){
        this.idCourse = idCourse;
        this.number = number;
        this.idProgram = idProgram;

    }

    public Long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Long idCourse) {
        this.idCourse = idCourse;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(Long idProgram) {
        this.idProgram = idProgram;
    }
}
