package com.youtan.leilao.service;

import com.youtan.leilao.DTO.VeiculoDTO;
import com.youtan.leilao.model.Veiculo;
import com.youtan.leilao.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VeiculoServiceImpl implements VeiculoService{

    private final VeiculoRepository veiculoRepository;
    private final ModelMapper mapper;

    @Override
    public List<VeiculoDTO> findAll() {
        return veiculoRepository.findAll().stream()
                .map(veiculo -> mapper.map(veiculo, VeiculoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VeiculoDTO getVeiculo(Long id) {
        return veiculoRepository.findById(id)
                .map(veiculo -> mapper.map(veiculo, VeiculoDTO.class))
                .orElseThrow(() -> new NoSuchElementException(" Veiculo não encontrado."));
    }

    @Override
    @Transactional
    public VeiculoDTO createVeiculo(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = mapper.map(veiculoDTO,Veiculo.class);
        veiculoRepository.save(veiculo);
        return mapper.map(veiculo,VeiculoDTO.class);
    }

    @Override
    @Transactional
    public VeiculoDTO updateVeiculo(Long id, VeiculoDTO veiculoDTO) {
        veiculoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(" Veiculo não encontrado."));
        Veiculo veiculo = mapper.map(veiculoDTO,Veiculo.class);
        return mapper.map(veiculo,VeiculoDTO.class);
    }

    @Override
    public void deleteVeiculo(Long id) {
        veiculoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(" Veiculo não encontrado."));
        veiculoRepository.deleteById(id);
    }
}
