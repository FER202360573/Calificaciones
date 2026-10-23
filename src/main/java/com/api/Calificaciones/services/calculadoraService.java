package com.api.Calificaciones.services;

import com.api.Calificaciones.models.calificacionModel;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class calculadoraService {

    public double calcularPromedioPeriodo(List<calificacionModel> calificaciones) {
        if (calificaciones == null || calificaciones.isEmpty()) {
            return 0.0;
        }
        double suma = 0.0;
        for (calificacionModel c : calificaciones) {
            suma += c.getCalificacion();
        }
        return suma / calificaciones.size();
    }
}