package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.ProfessorApi;
import org.example.desafiodiogo.dto.professor.LancarNotaRequest;
import org.example.desafiodiogo.service.NotaService;
import org.example.desafiodiogo.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ProfessorControllerImpl implements ProfessorApi {

    private final ProfessorService professorService;
    private final NotaService notaService;

    @Override
    public ResponseEntity<Map<String, Object>> dashboard() {
        Map<String, Object> dashboardData = professorService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }

    @Override
    public ResponseEntity<Map<String, Object>> alunosPorMateria(Long idMateria) {
        Map<String, Object> result = professorService.alunosPorMateria(idMateria);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<String> lancarNota(LancarNotaRequest request) {
        notaService.lancarNota(request);
        return ResponseEntity.ok("Nota lançada com sucesso");
    }

}
