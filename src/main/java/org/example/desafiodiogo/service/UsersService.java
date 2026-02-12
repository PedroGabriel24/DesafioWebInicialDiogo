package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.auth.ProfileJWTToken;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.model.Users;

public interface UsersService {

    Users loginUser(final String email);

    void cadastro(final UsersRequest request);

    ProfileJWTToken loadInfoUser(final Users user);

}
