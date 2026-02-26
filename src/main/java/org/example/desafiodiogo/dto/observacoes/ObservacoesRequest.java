package org.example.desafiodiogo.dto.observacoes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObservacoesRequest {

    private String mensagem;
    private Long alunoId;
    private String status;

}
