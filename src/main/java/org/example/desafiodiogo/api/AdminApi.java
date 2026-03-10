package org.example.desafiodiogo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.example.desafiodiogo.dto.aluno.AlunoSerieRequest;
import org.example.desafiodiogo.dto.aluno.AlunoSerieResponse;

import org.example.desafiodiogo.dto.materia.MateriaComProfessoresResponse;
import org.example.desafiodiogo.dto.materia.MateriaRequest;
import org.example.desafiodiogo.dto.materia.MateriaResponse;

import org.example.desafiodiogo.dto.professor.ProfessorComMateriasResponse;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieRequest;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieResponse;

import org.example.desafiodiogo.dto.serie.SerieRequest;
import org.example.desafiodiogo.dto.serie.SerieResponse;

import org.example.desafiodiogo.dto.seriemateria.SerieMateriaRequest;
import org.example.desafiodiogo.dto.seriemateria.SerieMateriaResponse;

import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.dto.users.UsersResponse;
import org.example.desafiodiogo.dto.users.UsersUpdateRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@SecurityRequirement(name = "bearerAuth")
public interface AdminApi {

    // USUÁRIOS

    @PostMapping("/usuarios")
    @Operation(summary = "Cria um usuário (apenas ADMIN)")
    ResponseEntity<UsersResponse> cadastro(@RequestBody UsersRequest request);

    @GetMapping("/usuarios")
    @Operation(summary = "Lista todos os usuários (apenas ADMIN)")
    ResponseEntity<List<UsersResponse>> listarUsuarios();

    @GetMapping("/usuarios/{id}")
    @Operation(summary = "Obtém um usuário pelo ID (apenas ADMIN)")
    ResponseEntity<UsersResponse> obterUsuarioPorId(@PathVariable Long id);

    @PutMapping("/usuarios/{id}")
    @Operation(summary = "Atualiza um usuário (apenas ADMIN)")
    ResponseEntity<UsersResponse> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsersUpdateRequest request);

    // ALUNO - SÉRIE

    @PostMapping("/alunos/series")
    @Operation(summary = "Adiciona um aluno a uma série (apenas ADMIN)")
    ResponseEntity<AlunoSerieResponse> adicionarAlunoASerie(@RequestBody AlunoSerieRequest request);

    // PROFESSOR - MATÉRIA - SÉRIE

    @PostMapping("/professores/series")
    @Operation(summary = "Adiciona um professor a uma matéria em uma série (apenas ADMIN)")
    ResponseEntity<ProfessorMateriaSerieResponse> adicionarProfessorAMateriaSerie(
            @RequestBody ProfessorMateriaSerieRequest request);

    @DeleteMapping("/professor-materias-series/{id}")
    @Operation(summary = "Deleta uma associação professor-matéria-série (apenas ADMIN)")
    ResponseEntity<Void> deletarProfessorMateriaSerie(@PathVariable Long id);

    @GetMapping("/professore-materias-series")
    @Operation(summary = "Listar as matérias de todos professores lesiona e suas séries (apenas ADMIN)")
    ResponseEntity<List<ProfessorComMateriasResponse>> listarProfessorMateriaSerie();

    // CRUD - SÉRIES

    @PostMapping("/series")
    @Operation(summary = "Cria uma nova série (apenas ADMIN)")
    ResponseEntity<SerieResponse> criarSerie(@RequestBody SerieRequest request);

    @GetMapping("/series")
    @Operation(summary = "Lista todas as séries (apenas ADMIN)")
    ResponseEntity<List<SerieResponse>> listarSeries();

    @GetMapping("/series/{id}")
    @Operation(summary = "Obtém uma série pelo ID (apenas ADMIN)")
    ResponseEntity<SerieResponse> obterSeriePorId(@PathVariable Long id);

    @PutMapping("/series/{id}")
    @Operation(summary = "Atualiza uma série (apenas ADMIN)")
    ResponseEntity<SerieResponse> atualizarSerie(@PathVariable Long id, @RequestBody SerieRequest request);

    @DeleteMapping("/series/{id}")
    @Operation(summary = "Deleta uma série (apenas ADMIN)")
    ResponseEntity<Void> deletarSerie(@PathVariable Long id);

    // CRUD - MATÉRIAS

    @PostMapping("/materias")
    @Operation(summary = "Cria uma nova matéria (apenas ADMIN)")
    ResponseEntity<MateriaResponse> criarMateria(@RequestBody MateriaRequest request);

    @GetMapping("/materias")
    @Operation(summary = "Lista todas as matérias (apenas ADMIN)")
    ResponseEntity<List<MateriaResponse>> listarMaterias();

    @GetMapping("/materias/{id}")
    @Operation(summary = "Obtém uma matéria pelo ID (apenas ADMIN)")
    ResponseEntity<MateriaResponse> obterMateriaPorId(@PathVariable Long id);

    @PutMapping("/materias/{id}")
    @Operation(summary = "Atualiza uma matéria (apenas ADMIN)")
    ResponseEntity<MateriaResponse> atualizarMateria(@PathVariable Long id, @RequestBody MateriaRequest request);

    @DeleteMapping("/materias/{id}")
    @Operation(summary = "Deleta uma matéria (apenas ADMIN)")
    ResponseEntity<Void> deletarMateria(@PathVariable Long id);

    // SÉRIE - MATÉRIA

    @PostMapping("/series-materias")
    @Operation(summary = "Cria uma nova associação série-matéria (apenas ADMIN)")
    ResponseEntity<SerieMateriaResponse> criarSerieMateria(@RequestBody SerieMateriaRequest request);

    @GetMapping("/series/{serieId}/materias")
    @Operation(summary = "Lista todas as matérias de uma série (apenas ADMIN)")
    ResponseEntity<List<SerieMateriaResponse>> listarSerieMateriasPorSerie(@PathVariable Long serieId);

    @GetMapping("/materias/{materiaId}/series")
    @Operation(summary = "Lista todas as séries de uma matéria (apenas ADMIN)")
    ResponseEntity<List<SerieMateriaResponse>> listarSerieMateriasPorMateria(@PathVariable Long materiaId);

    @DeleteMapping("/series-materias/{id}")
    @Operation(summary = "Deleta uma associação série-matéria (apenas ADMIN)")
    ResponseEntity<Void> deletarSerieMateria(@PathVariable Long id);

    @GetMapping("/professores/{professorId}/detalhes")
    @Operation(summary = "Obtém professor com lista de matérias e séries (apenas ADMIN)")
    ResponseEntity<ProfessorComMateriasResponse> obterProfessorComMateriasPorProfessorId(@PathVariable Long professorId);

    @GetMapping("/materias/{materiaId}/detalhes")
    @Operation(summary = "Obtém matéria com lista de professores e séries (apenas ADMIN)")
    ResponseEntity<MateriaComProfessoresResponse> obterMateriaComProfessoresPorMateriaId(@PathVariable Long materiaId);
}