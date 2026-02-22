package org.example.desafiodiogo.repository;

import org.example.desafiodiogo.model.SerieMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SerieMateriaRepository extends JpaRepository<SerieMateria, Long> {

    Optional<SerieMateria> findBySerieIdAndMateriaId(Long serieId, Long materiaId);

    List<SerieMateria> findBySerieId(Long serieId);

    List<SerieMateria> findByMateriaId(Long materiaId);

}

