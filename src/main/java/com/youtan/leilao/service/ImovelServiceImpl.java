package com.youtan.leilao.service;

import com.youtan.leilao.DTO.ImovelDTO;
import com.youtan.leilao.model.Imovel;
import com.youtan.leilao.repository.ImovelRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImovelServiceImpl implements ImovelService {

    private final ImovelRepository repositoryImovel;
    private final ModelMapper mapper;


    @Override
    public List<ImovelDTO> findAll() {
        return repositoryImovel.findAll().stream()
                .map(imovel -> mapper.map(imovel, ImovelDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ImovelDTO getImovel(Long id) {
        return repositoryImovel.findById(id)
                .map(imovel -> mapper.map(imovel,ImovelDTO.class))
                .orElseThrow(() -> new RuntimeException(" Imovel não encontrado"));
    }

    @Override
    @Transactional
    public ImovelDTO createImóvel(ImovelDTO imovelDTO) {
        Imovel imovel = mapper.map(imovelDTO,Imovel.class);
        repositoryImovel.save(imovel);

        return mapper.map(imovel,ImovelDTO.class);
    }

    @Override
    @Transactional
    public ImovelDTO updateImovel(ImovelDTO imovelDTO) {
        Imovel imovel = mapper.map(imovelDTO,Imovel.class);
        repositoryImovel.save(imovel);

        return mapper.map(imovel,ImovelDTO.class);
    }

    @Override
    public void deleteImovel(Long id) {
        repositoryImovel.deleteById(id);
    }
}
