package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.ProfessorApi;
import org.example.desafiodiogo.dto.observacoes.ObservacoesRequest;
import org.example.desafiodiogo.dto.observacoes.ObservacoesResponse;
import org.example.desafiodiogo.dto.professor.LancarNotaRequest;
import org.example.desafiodiogo.service.NotaService;
import org.example.desafiodiogo.service.ObservacoesService;
import org.example.desafiodiogo.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ProfessorControllerImpl implements ProfessorApi {

    private final ProfessorService professorService;
    private final NotaService notaService;
    private final ObservacoesService observacoesService;

    @Override
    public ResponseEntity<Map<String, Object>> dashboard(Optional<Long> disciplinaId, Optional<Long> turmaId) {
        Map<String, Object> dashboardData = professorService.getDashboardData(disciplinaId, turmaId);
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

    @Override
    public ResponseEntity<String> alterarNota(LancarNotaRequest request) {
        notaService.alterarNota(request);
        return ResponseEntity.ok("Nota alterada com sucesso");
    }

    @Override
    public ResponseEntity<String> adicionarObservacao(ObservacoesRequest request) {
        observacoesService.adicionarObservacao(request);
        return ResponseEntity.ok("Observação enviada com sucesso");
    }

    @Override
    public ResponseEntity<List<ObservacoesResponse>> listarObservacoes() {
        return ResponseEntity.ok(observacoesService.listarObservacoes());
    }

}
