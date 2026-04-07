package com.youtan.leilao.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youtan.leilao.model.Endereco;
import com.youtan.leilao.model.TipoImovel;

import java.math.BigDecimal;

public record ImovelDTO (

    Long id,
    TipoImovel tipoImovel,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00",locale = "pt-BR")
    BigDecimal preco,
    String nome,
    Endereco endereco,
    Integer metragem,
    Integer nQuartos,
    Integer nBanheiros

) implements ItemLeilaoDTO {}