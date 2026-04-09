package com.youtan.leilao.DTO;

import java.math.BigDecimal;

public record LanceDTO(
   Long idLeilao,
   Long idItem,
   BigDecimal valor,
   String cliente
) {}
