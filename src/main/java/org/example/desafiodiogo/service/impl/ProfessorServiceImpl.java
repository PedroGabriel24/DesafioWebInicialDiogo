package org.example.desafiodiogo.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieRequest;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieResponse;
import org.example.desafiodiogo.model.Materia;
import org.example.desafiodiogo.model.ProfessorMateriaSerie;
import org.example.desafiodiogo.model.Serie;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.*;
import org.example.desafiodiogo.service.ProfessorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorMateriaSerieRepository professorMateriaSerieRepository;
    private final UsersRepository usersRepository;
    private final MateriaRepository materiaRepository;
    private final SerieRepository serieRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ProfessorMateriaSerieResponse adicionarProfessorAMateriaSerie(ProfessorMateriaSerieRequest request) {
        var existing = professorMateriaSerieRepository.findByProfessorIdAndMateriaIdAndSerieId(
                request.getProfessorId(), request.getMateriaId(), request.getSerieId());
        if (existing.isPresent()) {
            throw new RuntimeException("Professor já está associado a esta matéria e série");
        }

        Users professor = usersRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + request.getProfessorId()));

        Materia materia = materiaRepository.findById(request.getMateriaId())
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada com id: " + request.getMateriaId()));

        Serie serie = serieRepository.findById(request.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada com id: " + request.getSerieId()));

        ProfessorMateriaSerie professorMateriaSerie = ProfessorMateriaSerie.builder()
                .professor(professor)
                .materia(materia)
                .serie(serie)
                .build();

        ProfessorMateriaSerie saved = professorMateriaSerieRepository.save(professorMateriaSerie);
        return new ProfessorMateriaSerieResponse(saved);
    }

    @Override
    @PreAuthorize("hasRole('PROFESSOR')")
    public Map<String, Object> getDashboardData() {
        Users user = getCurrentUser();

        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(QueryEnum.DASHBOARD.getQuery())
                .setParameter(1, user.getId())
                .getResultList();

        List<Map<String, Object>> materias = new ArrayList<>();
        for (Object[] r : rows) {
            String nome = r[0] != null ? r[0].toString() : null;
            Number totalAlunosNum = r[1] != null ? (Number) r[1] : 0;
            Object mediaObj = r[2];
            Number pendenciasNum = r[3] != null ? (Number) r[3] : 0;

            Double mediaTurma = null;
            if (mediaObj instanceof Number) {
                mediaTurma = ((Number) mediaObj).doubleValue();
            }

            Map<String, Object> m = new HashMap<>();
            m.put("nome", nome);
            m.put("totalAlunos", totalAlunosNum.intValue());
            m.put("mediaTurma", mediaTurma);
            m.put("pendencias", pendenciasNum.intValue());

            materias.add(m);
        }

        return Map.of("materias", materias);
    }

    @Override
    @PreAuthorize("hasRole('PROFESSOR')")
    public Map<String, Object> alunosPorMateria(final Long idMateria) {
        Users user = getCurrentUser();

        String verifySql = "SELECT COUNT(*) FROM professores_materias_series pms WHERE pms.user_id = ?1 AND pms.materia_id = ?2";
        Number count = ((Number) entityManager.createNativeQuery(verifySql)
                .setParameter(1, user.getId())
                .setParameter(2, idMateria)
                .getSingleResult());
        if (count == null || count.longValue() == 0L) {
            throw new RuntimeException("Professor não responsável por esta matéria");
        }

        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(QueryEnum.ALUNOS_POR_MATERIA.getQuery())
                .setParameter(1, user.getId())
                .setParameter(2, idMateria)
                .getResultList();

        List<Map<String, Object>> alunos = new ArrayList<>();
        for (Object[] r : rows) {
            // columns: aluno_series_id, aluno_id, aluno_nome, serie_nome, jaLancouNota
            Number alunoIdNum = r[1] != null ? (Number) r[1] : 0;
            String nome = r[2] != null ? r[2].toString() : null;
            String serie = r[3] != null ? r[3].toString() : null;
            Number jaLancouNum = r[4] != null ? (Number) r[4] : 0;

            Map<String, Object> m = new HashMap<>();
            m.put("id", alunoIdNum.longValue());
            m.put("nome", nome);
            m.put("serie", serie);
            m.put("jaLancouNota", jaLancouNum.intValue() == 1);

            alunos.add(m);
        }

        return Map.of("alunos", alunos);
    }

    private Users getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof Users) {
            return (Users) principal;
        }
        String email = auth.getName();
        return usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}

