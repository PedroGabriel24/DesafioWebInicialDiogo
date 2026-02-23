package org.example.desafiodiogo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.desafiodiogo.dto.professor.LancarNotaRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/professor")
public interface ProfessorApi {

    @GetMapping("/dashboard")
    @Operation(summary = "Retornar dados para Dashboard do professor", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<Map<String, Object>> dashboard();

    @GetMapping("/alunos-por-materia/{idMateria}")
    @Operation(summary = "Lista de alunos por matéria (apenas PROFESSOR responsável)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<Map<String, Object>> alunosPorMateria(@PathVariable Long idMateria);

    @PostMapping("/notas")
    @Operation(summary = "Lançar nota para um aluno (apenas PROFESSOR)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<String> lancarNota(@RequestBody LancarNotaRequest request);

    @PutMapping("/notas")
    @Operation(summary = "Alterar nota para um aluno (apenas PROFESSOR)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<String> alterarNota(@RequestBody LancarNotaRequest request);

}
