package com.youtan.leilao.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LanceHistoricoDTO {
    private Long id;
    private Object item;
    private BigDecimal valor;
    private String cliente;
}
