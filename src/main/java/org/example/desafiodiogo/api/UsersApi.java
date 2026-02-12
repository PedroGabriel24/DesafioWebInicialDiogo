package org.example.desafiodiogo.api;

import org.example.desafiodiogo.dto.users.UsersRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

public interface UsersApi {

    @PostMapping("/cadastro")
    @Operation(summary = "Cria um usuário (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<String> cadastro(
            @RequestBody UsersRequest request);

}
