package com.youtan.leilao.DTO;

import com.youtan.leilao.model.Leilao;
import com.youtan.leilao.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LanceHistoricoDTO {
    private Long id;
    private Object item;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private User user;
    private Leilao leilao;
}
