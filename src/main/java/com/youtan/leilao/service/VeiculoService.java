package com.youtan.leilao.service;

import com.youtan.leilao.DTO.VeiculoDTO;

import java.util.List;

public interface VeiculoService {
    List<VeiculoDTO> findAll();

    VeiculoDTO getVeiculo(Long id);

    VeiculoDTO createVeiculo(VeiculoDTO veiculoDTO);

    VeiculoDTO updateVeiculo(VeiculoDTO veiculoDTO);

    void deleteVeiculo(Long id);
}
