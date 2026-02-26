package org.example.desafiodiogo.dto.observacoes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.desafiodiogo.model.Observacoes;
import org.example.desafiodiogo.model.ProfileEnum;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObservacoesResponse {

    private String nome;
    private String mensagem;
    private String status;
    private LocalDateTime data;

    public ObservacoesResponse(Observacoes observacao, boolean destinatario) {
        this.nome = destinatario ? observacao.getAluno().getNome() : observacao.getProfessor().getNome();
        this.mensagem = observacao.getMensagem();
        this.status = observacao.getStatus();
        this.data = observacao.getData();
    }

}
