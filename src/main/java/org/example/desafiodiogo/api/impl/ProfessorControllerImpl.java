package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.ProfessorApi;
import org.example.desafiodiogo.service.AlunoService;
import org.example.desafiodiogo.service.ProfessorService;
import org.example.desafiodiogo.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ProfessorControllerImpl implements ProfessorApi {

    private final ProfessorService professorService;

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

}

