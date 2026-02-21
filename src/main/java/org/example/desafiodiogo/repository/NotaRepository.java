package org.example.desafiodiogo.repository;

import org.example.desafiodiogo.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findByAlunoSerieId(Long alunoSerieId);

    @Query("SELECT n FROM Nota n WHERE n.alunoSerie.aluno.id = :alunoId ORDER BY n.materia.nome, n.periodo.id")
    List<Nota> findByAlunoIdOrderByMateriaPeriodo(@Param("alunoId") Long alunoId);

    @Query("SELECT n FROM Nota n WHERE n.alunoSerie.serie.id = :serieId AND n.materia.id = :materiaId")
    List<Nota> findBySerieIdAndMateriaId(@Param("serieId") Long serieId, @Param("materiaId") Long materiaId);
}

