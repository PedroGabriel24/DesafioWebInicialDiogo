package org.example.desafiodiogo.repository;

import org.example.desafiodiogo.model.AlunosSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunosSerieRepository extends JpaRepository<AlunosSerie, Long> {

    Optional<AlunosSerie> findByAlunoIdAndSerieId(Long alunoId, Long serieId);

    List<AlunosSerie> findBySerieId(Long serieId);

    List<AlunosSerie> findByAlunoId(Long alunoId);

    @Query(value = "SELECT als.id, als.user_id AS aluno_id, u.nome AS aluno_nome, als.serie_id, s.nome AS serie_nome, " +
            "CASE WHEN n.id IS NOT NULL THEN true ELSE false END AS ja_lancou_nota " +
            "FROM alunos_series als " +
            "JOIN users u ON u.id = als.user_id " +
            "JOIN series s ON s.id = als.serie_id " +
            "LEFT JOIN notas n ON n.aluno_series_id = als.id AND n.materia_id = :materiaId " +
            "WHERE als.serie_id = :serieId " +
            "ORDER BY u.nome", nativeQuery = true)
    List<Object[]> findAlunosPorMateriaESerie(@Param("serieId") Long serieId, @Param("materiaId") Long materiaId);

}

