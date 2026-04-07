package com.youtan.leilao.service;


import com.youtan.leilao.DTO.LeilaoDTO;

import java.util.List;

public interface LeilaoService {
    List<LeilaoDTO> findAll();

    LeilaoDTO getLeilao(Long id);

    LeilaoDTO createLeilao(LeilaoDTO leilaoDTO);

    LeilaoDTO updateLeilao(Long id, LeilaoDTO leilaoDTO);

    void deleteLeilao(Long id);
}
