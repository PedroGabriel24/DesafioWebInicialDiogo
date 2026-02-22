package org.example.desafiodiogo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.desafiodiogo.dto.aluno.BoletimResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/aluno")
public interface AlunosApi {

    @GetMapping("/boletim")
    @Operation(summary = "Obtém o boletim do aluno autenticado (apenas ALUNO)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<BoletimResponse> obterBoletim();

    @GetMapping("/materias")
    @Operation(summary = "Lista matérias do aluno autenticado (apenas ALUNO)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<Object> listarMaterias();

    @GetMapping("/boletim/pdf")
    @Operation(summary = "Gera o boletim em PDF do aluno autenticado (apenas ALUNO)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<byte[]> gerarBoletimPdf();

}
