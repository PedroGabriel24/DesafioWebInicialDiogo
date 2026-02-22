package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.AdminApi;
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
import org.example.desafiodiogo.service.AdminService;
import org.example.desafiodiogo.service.AlunoService;
import org.example.desafiodiogo.service.MateriaService;
import org.example.desafiodiogo.service.ProfessorMateriaSerieListService;
import org.example.desafiodiogo.service.ProfessorService;
import org.example.desafiodiogo.service.SerieService;
import org.example.desafiodiogo.service.SerieMateriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminControllerImpl implements AdminApi {

    private final AdminService adminService;
    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final SerieService serieService;
    private final MateriaService materiaService;
    private final SerieMateriaService serieMateriaService;
    private final ProfessorMateriaSerieListService professorMateriaSerieListService;

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
    public ResponseEntity<Void> deletarUsuario(Long id) {
        adminService.deletarUsuario(id);
        return ResponseEntity.ok().build();
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

    @Override
    public ResponseEntity<SerieResponse> criarSerie(SerieRequest request) {
        SerieResponse response = serieService.criarSerie(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SerieResponse>> listarSeries() {
        List<SerieResponse> series = serieService.listarSeries();
        return ResponseEntity.ok(series);
    }

    @Override
    public ResponseEntity<SerieResponse> obterSeriePorId(Long id) {
        SerieResponse serie = serieService.obterSeriePorId(id);
        return ResponseEntity.ok(serie);
    }

    @Override
    public ResponseEntity<SerieResponse> atualizarSerie(Long id, SerieRequest request) {
        SerieResponse serie = serieService.atualizarSerie(id, request);
        return ResponseEntity.ok(serie);
    }

    @Override
    public ResponseEntity<Void> deletarSerie(Long id) {
        serieService.deletarSerie(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<MateriaResponse> criarMateria(MateriaRequest request) {
        MateriaResponse response = materiaService.criarMateria(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<MateriaResponse>> listarMaterias() {
        List<MateriaResponse> materias = materiaService.listarMaterias();
        return ResponseEntity.ok(materias);
    }

    @Override
    public ResponseEntity<MateriaResponse> obterMateriaPorId(Long id) {
        MateriaResponse materia = materiaService.obterMateriaPorId(id);
        return ResponseEntity.ok(materia);
    }

    @Override
    public ResponseEntity<MateriaResponse> atualizarMateria(Long id, MateriaRequest request) {
        MateriaResponse materia = materiaService.atualizarMateria(id, request);
        return ResponseEntity.ok(materia);
    }

    @Override
    public ResponseEntity<Void> deletarMateria(Long id) {
        materiaService.deletarMateria(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SerieMateriaResponse> criarSerieMateria(SerieMateriaRequest request) {
        SerieMateriaResponse response = serieMateriaService.criarSerieMateria(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SerieMateriaResponse>> listarSerieMateriasPorSerie(Long serieId) {
        List<SerieMateriaResponse> serieMaterias = serieMateriaService.listarSerieMateriasPorSerie(serieId);
        return ResponseEntity.ok(serieMaterias);
    }

    @Override
    public ResponseEntity<List<SerieMateriaResponse>> listarSerieMateriasPorMateria(Long materiaId) {
        List<SerieMateriaResponse> serieMaterias = serieMateriaService.listarSerieMateriasPorMateria(materiaId);
        return ResponseEntity.ok(serieMaterias);
    }

    @Override
    public ResponseEntity<Void> deletarSerieMateria(Long id) {
        serieMateriaService.deletarSerieMateria(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ProfessorComMateriasResponse> obterProfessorComMateriasPorProfessorId(Long professorId) {
        ProfessorComMateriasResponse response = professorMateriaSerieListService.obterProfessorComMateriasPorProfessorId(professorId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MateriaComProfessoresResponse> obterMateriaComProfessoresPorMateriaId(Long materiaId) {
        MateriaComProfessoresResponse response = professorMateriaSerieListService.obterMateriaComProfessoresPorMateriaId(materiaId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deletarProfessorMateriaSerie(Long id) {
        professorMateriaSerieListService.deletarProfessorMateriaSerie(id);
        return ResponseEntity.ok().build();
    }

}
