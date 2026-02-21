package org.example.desafiodiogo.api;

import org.example.desafiodiogo.dto.users.UsersRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Map;

public interface UsersApi {

    @PostMapping("/cadastro")
    @Operation(summary = "Cria um usuário (apenas ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<String> cadastro(
            @RequestBody UsersRequest request);

    @GetMapping("/professor/dashboard")
    @Operation(summary = "Retornar dados para Dashboard do professor", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<Map<String, Object>> dashboard();
}
