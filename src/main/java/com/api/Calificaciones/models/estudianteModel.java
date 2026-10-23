package com.api.Calificaciones.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiantes")
public class estudianteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstudiante;

    @Column(nullable = false, unique = true, length = 9)
    private String matricula;

    @Column(nullable = false, length = 15)
    private String contrasena;

    @Column(nullable = false)
    private String estatus;

    @Column(nullable = false, length = 30)
    private String periodoActual;

    public estudianteModel() {}

    public estudianteModel(Long idEstudiante, String matricula, String contrasena, String estatus, String periodoActual) {
        this.idEstudiante = idEstudiante;
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.estatus = estatus;
        this.periodoActual = periodoActual;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getPeriodoActual() {
        return periodoActual;
    }

    public void setPeriodoActual(String periodoActual) {
        this.periodoActual = periodoActual;
    }
}