package org.example.desafiodiogo.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancarNotaRequest {
    private Long alunoSerieId;
    private Long materiaId;
    private Long periodoId;
    private BigDecimal nota;
}

