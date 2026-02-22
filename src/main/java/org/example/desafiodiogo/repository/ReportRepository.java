package org.example.desafiodiogo.repository;

import org.example.desafiodiogo.model.ProfessorMateriaSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ProfessorMateriaSerie, Long> {

    @Query(value = "SELECT m.nome, " +
            " (SELECT COUNT(*) FROM alunos_series als WHERE als.serie_id = pms.serie_id) AS totalAlunos, " +
            " (SELECT AVG(n2.nota) FROM notas n2 JOIN alunos_series als2 ON n2.aluno_series_id = als2.id " +
            "    WHERE n2.materia_id = m.id AND als2.serie_id = pms.serie_id) AS mediaTurma, " +
            " (SELECT COUNT(*) FROM alunos_series als3 WHERE als3.serie_id = pms.serie_id " +
            "    AND NOT EXISTS (SELECT 1 FROM notas n3 WHERE n3.aluno_series_id = als3.id AND n3.materia_id = m.id)) AS pendencias " +
            "FROM professores_materias_series pms " +
            "JOIN materias m ON m.id = pms.materia_id " +
            "WHERE pms.user_id = :professorId", nativeQuery = true)
    List<Object[]> getDashboard(@Param("professorId") Long professorId);

    @Query(value = "SELECT als.id as aluno_series_id, u.id as aluno_id, u.nome as aluno_nome, s.nome as serie_nome, " +
            "CASE WHEN EXISTS (SELECT 1 FROM notas n WHERE n.aluno_series_id = als.id AND n.materia_id = :materiaId) THEN 1 ELSE 0 END as jaLancouNota " +
            "FROM professores_materias_series pms " +
            "JOIN series s ON s.id = pms.serie_id " +
            "JOIN alunos_series als ON als.serie_id = s.id " +
            "JOIN users u ON u.id = als.user_id " +
            "WHERE pms.materia_id = :materiaId AND pms.user_id = :professorId " +
            "ORDER BY u.nome", nativeQuery = true)
    List<Object[]> getAlunosPorMateria(@Param("professorId") Long professorId, @Param("materiaId") Long materiaId);

}
