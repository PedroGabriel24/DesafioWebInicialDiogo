package org.example.desafiodiogo.api;

import org.example.desafiodiogo.dto.users.UsersRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface UsersApi {

    @PostMapping("/cadastro")
    ResponseEntity<String> cadastro(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody UsersRequest request);

}
