package com.youtan.leilao.service;

import com.youtan.leilao.DTO.ImovelDTO;
import com.youtan.leilao.model.Imovel;
import com.youtan.leilao.repository.ImovelRepository;
import com.youtan.leilao.repository.LeilaoRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Data
@Service
public class ImovelServiceImpl implements ImovelService {

    private final ImovelRepository repositoryImovel;
    private final EnderecoService enderecoService;
    private final LeilaoRepository leilaoRepository;
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
                .orElseThrow(() -> new NoSuchElementException(" Imóvel não encontrado"));

    }

    @Override
    @Transactional
    public ImovelDTO createImovel(ImovelDTO imovelDTO) {
        Imovel imovel = mapper.map(imovelDTO,Imovel.class);
        imovel.setEndereco(enderecoService.validarEndereco(imovelDTO.endereco()));
        repositoryImovel.save(imovel);

        return mapper.map(imovel,ImovelDTO.class);
    }

    @Override
    @Transactional
    public ImovelDTO updateImovel(Long id, ImovelDTO imovelDTO) {
        repositoryImovel.findById(id)
                .orElseThrow(() -> new NoSuchElementException(" Imóvel não encontrado."));
        Imovel imovel = mapper.map(imovelDTO,Imovel.class);
        imovel.setEndereco(enderecoService.validarEndereco(imovelDTO.endereco()));
        repositoryImovel.save(imovel);

        return mapper.map(imovel,ImovelDTO.class);
    }

    @Override
    @Transactional
    public void deleteImovel(Long id) {
        repositoryImovel.findById(id)
                .orElseThrow(() -> new NoSuchElementException(" Imóvel não encontrado."));
        leilaoRepository.deleteItemReferenceFromJoinTable(id, "IMOVEL");

        repositoryImovel.deleteById(id);
    }
}
