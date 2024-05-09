package com.sena.riap.entities;
import jakarta.persistence.*;
import lombok.Builder;


@Entity
@Table(name = "user_course")
@Builder
public class UserCourse {

    @Id
    @Column(name = "id_user_course")
    private Long idUserCourse;

    //@ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "id_course")
    private Long idCourse;

    // @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "id_user")
    private Long idUser;

    public UserCourse() {
        // Constructor sin argumentos requerido por Hibernate
    }

    public UserCourse(Long idUserCourse, Long idCourse, Long idUser) {
        this.idUserCourse = idUserCourse;
        this.idCourse = idCourse;
        this.idUser = idUser;
    }

    public Long getIdUserCourse() {
        return idUserCourse;
    }

    public void setIdUserCourse(Long idUserCourse) {
        this.idUserCourse = idUserCourse;
    }

    public Long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Long idCourse) {
        this.idCourse = idCourse;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
