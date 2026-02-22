package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.AlunosApi;
import org.example.desafiodiogo.dto.aluno.BoletimResponse;
import org.example.desafiodiogo.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AlunosControllerImpl implements AlunosApi {

    private final AlunoService alunoService;

    @Override
    public ResponseEntity<BoletimResponse> obterBoletim() {
        BoletimResponse boletim = alunoService.obterBoletimAluno();
        return ResponseEntity.ok(boletim);
    }

    @Override
    public ResponseEntity<Object> listarMaterias() {
        return ResponseEntity.ok(alunoService.listarMaterias());
    }
}
