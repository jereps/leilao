package com.youtan.leilao.DTO;

import com.youtan.leilao.model.Endereco;
import com.youtan.leilao.model.TipoCategoria;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LeilaoDTO {
    private Long id;
    private String nome;
    private LocalDateTime dataHorarioLeilao;
    private Endereco enderecoLeilaoDTO;
    private List<ItemLeilaoDTO> mercadoria;
    private TipoCategoria categoria;
    private String descricao;
}
