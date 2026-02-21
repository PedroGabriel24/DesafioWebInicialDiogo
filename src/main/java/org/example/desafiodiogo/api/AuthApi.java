package org.example.desafiodiogo.api;

import jakarta.validation.Valid;
import org.example.desafiodiogo.dto.auth.AuthRequestParams;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
public interface AuthApi {

    @PostMapping("/login")
    String loginUser(@Valid @RequestBody AuthRequestParams authRequestParams);

}
