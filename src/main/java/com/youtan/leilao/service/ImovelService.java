package com.youtan.leilao.service;

import com.youtan.leilao.DTO.ImovelDTO;

import java.util.List;

public interface ImovelService {
    List<ImovelDTO> findAll();

    ImovelDTO getImovel(Long id);

    ImovelDTO createImovel(ImovelDTO imovelDTO);

    ImovelDTO updateImovel(ImovelDTO imovelDTO);

    void deleteImovel(Long id);
}
