package com.youtan.leilao.DTO;

import com.youtan.leilao.model.Bairro;
import com.youtan.leilao.model.CEP;
import com.youtan.leilao.model.Cidade;
import com.youtan.leilao.model.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record EnderecoDTO(
    Long id,
    String numero,
    String rua,
    String complemento,
    CEP cep,
    Bairro bairro,
    Cidade cidade,
    Estado estado
) {}
