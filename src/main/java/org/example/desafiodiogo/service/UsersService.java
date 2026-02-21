package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.auth.ProfileJWTToken;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.model.Users;

import java.util.Map;

public interface UsersService {

    Users loginUser(final String email);

    ProfileJWTToken loadInfoUser(final Users user);

}
