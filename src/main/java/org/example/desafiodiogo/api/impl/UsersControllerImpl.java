package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.UsersApi;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UsersControllerImpl implements UsersApi {

    private final UsersService usersService;

    @Override
    public ResponseEntity<String> cadastro(String token, UsersRequest request) {
        usersService.cadastro(request);
        return ResponseEntity.ok("ok");
    }

}
