package com.api.Calificaciones.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "calificaciones")
public class calificacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalificacion;

    @Column(nullable = false)
    private Long idEstudiante;

    @Column(nullable = false)
    private String materia;

    @Column(nullable = false)
    private Double calificacion;

    @Column(nullable = false)
    private String estatus;

    @Column(nullable = false, length = 30)
    private String periodo;

    public calificacionModel() {}

    public calificacionModel(Long idCalificacion, Long idEstudiante, String materia, Double calificacion, String estatus, String periodo) {
        this.idCalificacion = idCalificacion;
        this.idEstudiante = idEstudiante;
        this.materia = materia;
        this.calificacion = calificacion;
        this.estatus = estatus;
        this.periodo = periodo;
    }

    public Long getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Long idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}