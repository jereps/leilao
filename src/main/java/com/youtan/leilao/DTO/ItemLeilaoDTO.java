package com.youtan.leilao.DTO;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImovelDTO.class, name = "IMOVEL"),
        @JsonSubTypes.Type(value = VeiculoDTO.class,name = "VEICULO")
})
public interface ItemLeilaoDTO {
}
