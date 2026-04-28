package com.youtan.leilao.service;


import com.youtan.leilao.DTO.ItemLeilaoDTO;
import com.youtan.leilao.DTO.LanceDTO;
import com.youtan.leilao.DTO.LanceHistoricoDTO;
import com.youtan.leilao.DTO.LeilaoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeilaoService {
    List<LeilaoDTO> findAll();

    LeilaoDTO getLeilao(Long id);

    List<ItemLeilaoDTO> getLeilaoItens(Long id);

    LeilaoDTO createLeilao(LeilaoDTO leilaoDTO);

    LeilaoDTO updateLeilao(Long id, LeilaoDTO leilaoDTO);

    void deleteLeilao(Long id);

    public void addItensAoLeilao(Long leilaoId, List<ItemLeilaoDTO> novosItens,String tipo);

    void novoLance(LanceDTO lance);

    List<LanceHistoricoDTO> getHistorico(Long id, String tipo);
}
