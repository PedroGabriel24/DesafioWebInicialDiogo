package org.example.desafiodiogo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.desafiodiogo.dto.aluno.AlunoMateriaPorSerieResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/professor")
public interface ProfessorApi {

    @GetMapping("/dashboard")
    @Operation(summary = "Retornar dados para Dashboard do professor", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<Map<String, Object>> dashboard();

    @GetMapping("/alunos-por-materia/{idMateria}")
    @Operation(summary = "Lista de alunos por matéria (apenas PROFESSOR responsável)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<Map<String, Object>> alunosPorMateria(@PathVariable("idMateria") Long idMateria);

}

