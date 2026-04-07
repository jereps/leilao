package com.youtan.leilao.service;

import com.youtan.leilao.DTO.ImovelDTO;
import com.youtan.leilao.DTO.ItemLeilaoDTO;
import com.youtan.leilao.DTO.LeilaoDTO;
import com.youtan.leilao.DTO.VeiculoDTO;
import com.youtan.leilao.model.Imovel;
import com.youtan.leilao.model.Leilao;
import com.youtan.leilao.model.Veiculo;
import com.youtan.leilao.repository.ImovelRepository;
import com.youtan.leilao.repository.LeilaoRepository;
import com.youtan.leilao.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeilaoServiceImpl implements LeilaoService {

    private final LeilaoRepository leilaoRepository;
    private ImovelRepository imovelRepository;
    private VeiculoRepository veiculoRepository;
    private final ModelMapper mapper;


    @Override
    public List<LeilaoDTO> findAll() {
        return leilaoRepository.findAll().stream()
                .map(leilao -> converterParaDTO(leilao))
                .collect(Collectors.toList());
    }

    @Override
    public LeilaoDTO getLeilao(Long id) {
        return leilaoRepository.findById(id)
                .map(leilao -> converterParaDTO(leilao))
                .orElseThrow(() -> new EntityNotFoundException(" Leilão não encontrado"));
    }

    @Override
    @Transactional
    public LeilaoDTO createLeilao(LeilaoDTO leilaoDTO) {
        Leilao leilao = mapper.map(leilaoDTO,Leilao.class);
        leilaoRepository.save(leilao);

        return mapper.map(leilao,LeilaoDTO.class);
    }

    @Override
    @Transactional
    public LeilaoDTO updateLeilao(Long id, LeilaoDTO leilaoDTO) {
        leilaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(" Leilão não encontrado."));
        Leilao leilao = mapper.map(leilaoDTO,Leilao.class);
        leilaoRepository.save(leilao);

        return mapper.map(leilao,LeilaoDTO.class);
    }

    @Override
    public void deleteLeilao(Long id) {
        leilaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(" Leilão não encontrado."));
        leilaoRepository.deleteById(id);
    }

    public void criarLeilaoComItens(String descricao, List<Long> ids, String tipo) {
        Leilao leilao = new Leilao();
        leilao.setDescricao(descricao);
        leilao.setItens(new ArrayList<>());

        if ("IMOVEL".equalsIgnoreCase(tipo)) {
            for (Long id : ids) {
                // getReferenceById é mais performático aqui: não faz SELECT,
                // apenas cria um proxy com o ID para salvar na tabela de ligação
                Imovel imovel = imovelRepository.getReferenceById(id);
                leilao.getItens().add(imovel);
            }
        } else if ("VEICULO".equalsIgnoreCase(tipo)) {
            for (Long id : ids) {
                Veiculo veiculo = veiculoRepository.getReferenceById(id);
                leilao.getItens().add(veiculo);
            }
        }

        leilaoRepository.save(leilao);
    }

    public void adicionarItensAoLeilao(Long leilaoId, List<Object> novosItens) {
        Leilao leilao = leilaoRepository.findById(leilaoId)
                .orElseThrow(() -> new EntityNotFoundException("Leilao não encontrado"));

        // Regra: Não permitir misturar Imóvel e Veículo na mesma lista
        validarTipoUnico(leilao, novosItens);

        leilao.getItens().addAll(novosItens);
        leilaoRepository.save(leilao);
    }

    private void validarTipoUnico(Leilao leilao, List<Object> novosItens) {
        if (novosItens.isEmpty()) return;

        // Pega o tipo do primeiro item da nova lista
        Class<?> tipoNovo = novosItens.get(0).getClass();

        // Se o leilão já tiver itens, verifica se o tipo é o mesmo
        if (!leilao.getItens().isEmpty()) {
            Class<?> tipoExistente = leilao.getItens().get(0).getClass();
            if (!tipoExistente.equals(tipoNovo)) {
                throw new RuntimeException("Este leilão já possui itens do tipo "
                        + tipoExistente.getSimpleName() + ". Não é possível misturar.");
            }
        }
    }


    public LeilaoDTO converterParaDTO(Leilao leilao) {
        // Mapeia os campos básicos (id, descricao)
        LeilaoDTO dto = mapper.map(leilao, LeilaoDTO.class);

        // Mapeia a lista heterogênea usando o switch do Java 25
        List<ItemLeilaoDTO> itensDTO = leilao.getItens().stream()
                .map(item -> switch (item) {
                    case Imovel i  -> mapper.map(i, ImovelDTO.class);
                    case Veiculo v -> mapper.map(v, VeiculoDTO.class);
                    case null      -> null;
                    default        -> throw new IllegalStateException("Tipo não suportado");
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        dto.setItensDTO(itensDTO);
        return dto;
    }
}
