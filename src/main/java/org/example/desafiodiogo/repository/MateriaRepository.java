package org.example.desafiodiogo.repository;

import org.example.desafiodiogo.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @Override
    @Query("SELECT m FROM Materia m WHERE m.id = :id and m.status = 'ATIVO'")
    Optional<Materia> findById(Long id);
}

