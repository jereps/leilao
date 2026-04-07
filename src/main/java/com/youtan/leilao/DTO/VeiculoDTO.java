package com.youtan.leilao.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youtan.leilao.model.TipoCombustivel;
import com.youtan.leilao.model.TipoVeículo;

import java.math.BigDecimal;

public record VeiculoDTO (
    Long id,
    String placa,
    String marcaModelo,
    Integer anoFabricacao,
    String Cor,
    TipoCombustivel tipoCombustível,
    TipoVeículo tipoVeículo,
    Integer nPortas,
    Integer qtdPassageiros,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00",locale = "pt-BR")
    BigDecimal valor
) implements ItemLeilaoDTO {}
