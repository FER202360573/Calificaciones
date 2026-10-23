package com.api.Calificaciones.services;

import com.api.Calificaciones.models.calificacionModel;
import com.api.Calificaciones.models.estudianteModel;
import com.api.Calificaciones.repositories.calificacionRepository;
import com.api.Calificaciones.repositories.estudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class consultaCalificacionesService {

    @Autowired
    private estudianteRepository estudianteRepo;

    @Autowired
    private calificacionRepository calificacionRepo;

    @Transactional(readOnly = true)
    public List<calificacionModel> consultarCalificaciones(String matricula, String contrasena, String periodo) throws Exception {

        // Validación manual de campos requeridos
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new Exception("MATRICULA_REQUERIDA");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new Exception("CONTRASEÑA_REQUERIDA");
        }
        if (periodo == null || periodo.trim().isEmpty()) {
            throw new Exception("PERIODO_REQUERIDO");
        }

        // 1. Buscar estudiante por matrícula
        Optional<estudianteModel> estudianteOpt = estudianteRepo.findByMatricula(matricula);
        if (estudianteOpt.isEmpty()) {
            throw new Exception("MATRICULA_NO_ENCONTRADA");
        }

        estudianteModel estudiante = estudianteOpt.get();

        // 2. Validar contraseña
        if (!estudiante.getContrasena().equals(contrasena)) {
            throw new Exception("CONTRASEÑA_INCORRECTA");
        }

        // 3. Validar estatus
        if (!"ACTIVO".equalsIgnoreCase(estudiante.getEstatus())) {
            throw new Exception("ESTUDIANTE_INACTIVO");
        }

        // 4. Consultar calificaciones en la base de datos
        List<calificacionModel> calificaciones = calificacionRepo.findByIdEstudianteAndPeriodo(estudiante.getIdEstudiante(), periodo);
        if (calificaciones.isEmpty()) {
            throw new Exception("PERIODO_NO_ENCONTRADO");
        }

        return calificaciones;
    }
}