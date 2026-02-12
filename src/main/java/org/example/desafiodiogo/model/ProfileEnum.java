package org.example.desafiodiogo.model;

import lombok.Getter;

@Getter
public enum ProfileEnum {

    ADMIN("Administrador"),
    PROFESSOR("Professor"),
    ALUNO("Aluno");

    final String profileName;

    ProfileEnum(String profileName) {
        this.profileName = profileName;
    }
}
