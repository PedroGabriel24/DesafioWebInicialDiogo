package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.AdminApi;
import org.example.desafiodiogo.dto.aluno.AlunoSerieRequest;
import org.example.desafiodiogo.dto.aluno.AlunoSerieResponse;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieRequest;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieResponse;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.dto.users.UsersResponse;
import org.example.desafiodiogo.dto.users.UsersUpdateRequest;
import org.example.desafiodiogo.service.AdminService;
import org.example.desafiodiogo.service.AlunoService;
import org.example.desafiodiogo.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminControllerImpl implements AdminApi {

    private final AdminService adminService;
    private final AlunoService alunoService;
    private final ProfessorService professorService;

    @Override
    public ResponseEntity<UsersResponse> cadastro(UsersRequest request) {
        UsersResponse response = adminService.cadastro(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<UsersResponse>> listarUsuarios() {
        List<UsersResponse> usuarios = adminService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Override
    public ResponseEntity<UsersResponse> obterUsuarioPorId(Long id) {
        UsersResponse usuario = adminService.obterUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Override
    public ResponseEntity<UsersResponse> atualizarUsuario(Long id, UsersUpdateRequest request) {
        UsersResponse usuario = adminService.atualizarUsuario(id, request);
        return ResponseEntity.ok(usuario);
    }

    @Override
    public ResponseEntity<String> deletarUsuario(Long id) {
        adminService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    @Override
    public ResponseEntity<AlunoSerieResponse> adicionarAlunoASerie(AlunoSerieRequest request) {
        AlunoSerieResponse response = alunoService.adicionarAlunoASerie(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ProfessorMateriaSerieResponse> adicionarProfessorAMateriaSerie(ProfessorMateriaSerieRequest request) {
        ProfessorMateriaSerieResponse response = professorService.adicionarProfessorAMateriaSerie(request);
        return ResponseEntity.ok(response);
    }

}
