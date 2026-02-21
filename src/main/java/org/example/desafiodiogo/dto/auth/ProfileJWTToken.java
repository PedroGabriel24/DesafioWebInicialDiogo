package org.example.desafiodiogo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.desafiodiogo.model.Users;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileJWTToken {

    private String name;
    private String email;
    private String profile;
    private Map<String, Object> payload;

    public ProfileJWTToken(final Users info) {
        this.name = info.getNome();
        this.email = info.getEmail();
        this.profile = info.getTipo().getProfileName();
    }
}
