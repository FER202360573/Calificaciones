package com.api.Calificaciones.repositories;

import com.api.Calificaciones.models.calificacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface calificacionRepository extends JpaRepository<calificacionModel, Long> {
    List<calificacionModel> findByIdEstudianteAndPeriodo(Long idEstudiante, String periodo);
}