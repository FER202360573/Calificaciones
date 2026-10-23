package com.api.Calificaciones.controllers;

import com.api.Calificaciones.models.calificacionModel;
import com.api.Calificaciones.services.consultaCalificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calificaciones")
public class consultaCalificacionesController {

    @Autowired
    private consultaCalificacionesService consultaService;

    @GetMapping("/consultar")
    public List<calificacionModel> consultar(
            @RequestParam String matricula,
            @RequestParam String contrasena,
            @RequestParam String periodo) {
        try {
            return consultaService.consultarCalificaciones(matricula, contrasena, periodo);
        } catch (Exception e) {
            System.err.println("Error en la consulta: " + e.getMessage());
            return null;
        }
    }
}