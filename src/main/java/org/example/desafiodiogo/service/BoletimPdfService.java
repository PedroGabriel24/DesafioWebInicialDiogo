package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.aluno.BoletimResponse;

import java.io.IOException;

public interface BoletimPdfService {

    byte[] gerarPdfBoletim(BoletimResponse boletim) throws IOException;

}

