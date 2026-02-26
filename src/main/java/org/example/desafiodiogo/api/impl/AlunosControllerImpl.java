package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.AlunosApi;
import org.example.desafiodiogo.dto.aluno.BoletimResponse;
import org.example.desafiodiogo.dto.observacoes.ObservacoesResponse;
import org.example.desafiodiogo.service.AlunoService;
import org.example.desafiodiogo.service.ObservacoesService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlunosControllerImpl implements AlunosApi {

    private final AlunoService alunoService;
    private final ObservacoesService observacoesService;

    @Override
    public ResponseEntity<BoletimResponse> obterBoletim() {
        BoletimResponse boletim = alunoService.obterBoletimAluno();
        return ResponseEntity.ok(boletim);
    }

    @Override
    public ResponseEntity<Object> listarMaterias() {
        return ResponseEntity.ok(alunoService.listarMaterias());
    }

    @Override
    public ResponseEntity<byte[]> gerarBoletimPdf() {
        try {
            byte[] pdfBytes = alunoService.gerarBoletimPdf();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename("boletim.pdf", StandardCharsets.UTF_8)
                            .build()
            );
            headers.setContentLength(pdfBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<List<ObservacoesResponse>> listarObservacoes() {
        return ResponseEntity.ok(observacoesService.listarObservacoes());
    }
}
