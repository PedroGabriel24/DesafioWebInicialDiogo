package org.example.desafiodiogo.repository;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.desafiodiogo.model.ProfessorMateriaSerie;

@Repository
public interface ProfessorMateriaSerieRepository extends JpaRepository<ProfessorMateriaSerie, Long> {

    Optional<ProfessorMateriaSerie> findByProfessorIdAndMateriaIdAndSerieId(Long professorId, Long materiaId, Long serieId);

    long countByProfessorIdAndMateriaId(Long professorId, Long materiaId);

}
