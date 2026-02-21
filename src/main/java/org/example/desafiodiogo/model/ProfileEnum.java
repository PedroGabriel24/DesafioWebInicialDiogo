package org.example.desafiodiogo.model;

import lombok.Getter;

@Getter
public enum ProfileEnum {

    ADMIN("Administrador"),
    PROFESSOR("Professor"),
    ALUNO("Aluno");

    String profileName;

    ProfileEnum(String administrador) {
    }
}
