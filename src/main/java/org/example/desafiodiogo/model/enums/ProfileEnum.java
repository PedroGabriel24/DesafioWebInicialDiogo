package org.example.desafiodiogo.model.enums;

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
