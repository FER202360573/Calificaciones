package com.api.Calificaciones.repositories;

import com.api.Calificaciones.models.estudianteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface estudianteRepository extends JpaRepository<estudianteModel, Long> {
    Optional<estudianteModel> findByMatricula(String matricula);
}