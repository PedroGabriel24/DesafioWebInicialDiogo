package org.example.desafiodiogo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.desafiodiogo.dto.aluno.AlunoSerieRequest;
import org.example.desafiodiogo.dto.aluno.AlunoSerieResponse;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieRequest;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieResponse;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.dto.users.UsersResponse;
import org.example.desafiodiogo.dto.users.UsersUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
public interface AdminApi {

    @PostMapping("/cadastro")
    @Operation(summary = "Cria um usuário (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<UsersResponse> cadastro(
            @RequestBody UsersRequest request);

    @GetMapping("/usuarios")
    @Operation(summary = "Lista todos os usuários (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<List<UsersResponse>> listarUsuarios();

    @GetMapping("/usuarios/{id}")
    @Operation(summary = "Obtém um usuário pelo ID (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<UsersResponse> obterUsuarioPorId(@PathVariable Long id);

    @PutMapping("/usuarios/{id}")
    @Operation(summary = "Atualiza um usuário (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<UsersResponse> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsersUpdateRequest request);

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Deleta um usuário (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<String> deletarUsuario(@PathVariable Long id);

    @PostMapping("/alunos/serie")
    @Operation(summary = "Adiciona um aluno a uma série (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<AlunoSerieResponse> adicionarAlunoASerie(@RequestBody AlunoSerieRequest request);

    @PostMapping("/professor/serie")
    @Operation(summary = "Adiciona um professor a uma matéria em uma série (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<ProfessorMateriaSerieResponse> adicionarProfessorAMateriaSerie(@RequestBody ProfessorMateriaSerieRequest request);

}
