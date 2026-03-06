package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.model.enums.ProfileEnum;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.UsersRepository;
import org.example.desafiodiogo.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public Users findUsersByEmail(final String email) {
        return usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Senha ou Email incorretos."));
    }

    public Users findUsersById(final Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com id: " + id));
    }

    public List<Map<String, Object>> loadInfoUser(final String email, final ProfileEnum tipo) {
        List<Map<String, Object>> payload = new ArrayList<>();
        if (tipo != ProfileEnum.ADMIN) {
            List<Object[]> rows = usersRepository.loadInfoUser(email, tipo.name());
            if (rows != null) {
                for (Object[] r : rows) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("tipo", r[0] != null ? r[0].toString() : null);
                    m.put("materia", r[1] != null ? r[1].toString() : null);
                    m.put("materiaId", r[2] != null ? r[2].toString() : null);
                    m.put("serie", r[3] != null ? r[3].toString() : null);
                    payload.add(m);
                }
            }
        }

        return payload;
    }
}
