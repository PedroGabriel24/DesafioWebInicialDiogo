package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.auth.ProfileJWTToken;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.model.ProfileEnum;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.UsersRepository;
import org.example.desafiodiogo.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Users loginUser(final String email) {
        return usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> new RuntimeException("Senha ou Email incorretos."));
    }

    public void cadastro(final UsersRequest request) {
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }
        Users user = Users.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(AuthServiceImpl.encodePassword(request.getSenha()))
                .cpf(request.getCpf())
                .cadastro(LocalDateTime.now())
                .nascimento(LocalDate.parse(request.getNascimento()))
                .tipo(ProfileEnum.valueOf(request.getTipo()))
                .telefone(request.getTelefone())
                .status("A")
                .build();

        usersRepository.save(user);
    }

    public ProfileJWTToken loadInfoUser(final Users user) {
        ProfileJWTToken token = new ProfileJWTToken(user);

        if (user.getTipo() != ProfileEnum.ADMIN) {
            var payload = usersRepository.loadInfoUser(user.getEmail(), user.getTipo().getProfileName());
            token.setExtras(payload);
        }

        return token;
    }

    @Override
    public Map<String, Object> getDashboardData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        Users user = null;
        Object principal = auth.getPrincipal();
        if (principal instanceof Users) {
            user = (Users) principal;
        } else {
            // Fallback: use authentication name as email
            String email = auth.getName();
            user = usersRepository.findUsersByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        }

        if (user.getTipo() != ProfileEnum.PROFESSOR) {
            throw new RuntimeException("Acesso negado: apenas professores podem acessar este dashboard");
        }

        String sql = "SELECT m.nome, " +
                " (SELECT COUNT(*) FROM alunos_series als WHERE als.serie_id = pms.serie_id) AS totalAlunos, " +
                " (SELECT AVG(n2.nota) FROM notas n2 JOIN alunos_series als2 ON n2.aluno_series_id = als2.id " +
                "    WHERE n2.materia_id = m.id AND als2.serie_id = pms.serie_id) AS mediaTurma, " +
                " (SELECT COUNT(*) FROM alunos_series als3 WHERE als3.serie_id = pms.serie_id " +
                "    AND NOT EXISTS (SELECT 1 FROM notas n3 WHERE n3.aluno_series_id = als3.id AND n3.materia_id = m.id)) AS pendencias " +
                "FROM professores_materias_series pms " +
                "JOIN materias m ON m.id = pms.materia_id " +
                "WHERE pms.user_id = ?1";

        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter(1, user.getId())
                .getResultList();

        List<Map<String, Object>> materias = new ArrayList<>();
        for (Object[] r : rows) {
            String nome = r[0] != null ? r[0].toString() : null;
            Number totalAlunosNum = r[1] != null ? (Number) r[1] : 0;
            Object mediaObj = r[2];
            Number pendenciasNum = r[3] != null ? (Number) r[3] : 0;

            Double mediaTurma = null;
            if (mediaObj != null && mediaObj instanceof Number) {
                mediaTurma = ((Number) mediaObj).doubleValue();
            }

            Map<String, Object> m = new HashMap<>();
            m.put("nome", nome);
            m.put("totalAlunos", totalAlunosNum.intValue());
            m.put("mediaTurma", mediaTurma);
            m.put("pendencias", pendenciasNum.intValue());

            materias.add(m);
        }

        return Map.of("materias", materias);
    }

}
