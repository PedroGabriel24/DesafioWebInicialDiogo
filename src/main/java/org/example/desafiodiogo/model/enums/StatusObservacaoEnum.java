package org.example.desafiodiogo.model.enums;

import lombok.Getter;

@Getter
public enum StatusObservacaoEnum {

    POSITIVA("Positiva"),
    NEGATIVA("Negativa"),
    NEUTRA("Neutra");

    private final String descricao;

    StatusObservacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}
