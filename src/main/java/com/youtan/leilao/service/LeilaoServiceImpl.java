package com.youtan.leilao.service;

import com.youtan.leilao.DTO.ImovelDTO;
import com.youtan.leilao.DTO.ItemLeilaoDTO;
import com.youtan.leilao.DTO.LeilaoDTO;
import com.youtan.leilao.DTO.VeiculoDTO;
import com.youtan.leilao.model.Endereco;
import com.youtan.leilao.model.Imovel;
import com.youtan.leilao.model.Leilao;
import com.youtan.leilao.model.Veiculo;
import com.youtan.leilao.repository.ImovelRepository;
import com.youtan.leilao.repository.LeilaoRepository;
import com.youtan.leilao.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class LeilaoServiceImpl implements LeilaoService {

    private final LeilaoRepository leilaoRepository;
    private final ImovelRepository imovelRepository;
    private final VeiculoRepository veiculoRepository;
    private final EnderecoService enderecoService;
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
        leilao.setItens(new ArrayList<>());
        leilao.setEnderecoLeilao(enderecoService.validarEndereco(leilaoDTO.getEnderecoLeilaoDTO()));
        validarTipoUnico(leilao,leilaoDTO.getMercadoria());


        converterItensLeilao(leilao,leilaoDTO);
//        leilao.getItens().addAll(leilaoDTO.getItensDTO());
        leilaoRepository.save(leilao);

        return converterParaDTO(leilao);
    }

    @Override
    @Transactional
    public LeilaoDTO updateLeilao(Long id, LeilaoDTO leilaoDTO) {
        leilaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(" Leilão não encontrado."));
        Leilao leilao = mapper.map(leilaoDTO,Leilao.class);
        leilao.setEnderecoLeilao(enderecoService.validarEndereco(leilaoDTO.getEnderecoLeilaoDTO()));
        validarTipoUnico(leilao,leilaoDTO.getMercadoria());
        leilao.getItens().addAll(leilaoDTO.getMercadoria());
        leilaoRepository.save(leilao);

        return converterParaDTO(leilao);
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

    public void addItensAoLeilao(Long leilaoId, List<ItemLeilaoDTO> novosItens, String tipo) {
        Leilao leilao = leilaoRepository.findById(leilaoId)
                .orElseThrow(() -> new EntityNotFoundException("Leilao não encontrado"));

        // Regra: Não permitir misturar Imóvel e Veículo na mesma lista
        validarTipoUnico(leilao, novosItens);

        if ("IMOVEL".equalsIgnoreCase(tipo)) {
            for (ItemLeilaoDTO item : novosItens) {
                // getReferenceById é mais performático aqui: não faz SELECT,
                // apenas cria um proxy com o ID para salvar na tabela de ligação
                Imovel imovel = imovelRepository.getReferenceById(((ImovelDTO) item).id());
                leilao.getItens().add(imovel);
            }
        } else if ("VEICULO".equalsIgnoreCase(tipo)) {
            for (ItemLeilaoDTO item : novosItens) {
                Veiculo veiculo = veiculoRepository.getReferenceById(((VeiculoDTO)item).id());
                leilao.getItens().add(veiculo);
            }
        }

        leilaoRepository.save(leilao);
    }

    private void validarTipoUnico(Leilao leilao, List<ItemLeilaoDTO> novosItens) {
        if (novosItens.isEmpty()) return;


        // Pega o tipo do primeiro item da nova lista
        var tipoNovo = novosItens.get(0);
        var itensDTO = new Object();
        itensDTO = switch (tipoNovo) {
            // O ModelMapper converte os dados, o Jackson coloca o "tipo" no JSON
            case ImovelDTO i  -> mapper.map(i, Imovel.class);
            case VeiculoDTO v -> mapper.map(v, Veiculo.class);
            case null      -> null;
            default        -> throw new IllegalStateException("Tipo não suportado");
        };

        // Se o leilão já tiver itens, verifica se o tipo é o mesmo
        if (!leilao.getItens().isEmpty()) {
            var tipoExistente = leilao.getItens().get(0);
            if (!(tipoExistente.equals(tipoNovo))) {
                throw new RuntimeException("Este leilão já possui itens do tipo "
                        + tipoExistente.getClass().getSimpleName() + ". Não é possível misturar.");
            }
        }
    }


    public LeilaoDTO converterParaDTO(Leilao leilao) {
        // Mapeia os campos básicos (id, descricao)
        LeilaoDTO dto = mapper.map(leilao, LeilaoDTO.class);
        dto.setMercadoria(new ArrayList<>());
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

        dto.setEnderecoLeilaoDTO(leilao.getEnderecoLeilao());
        dto.getMercadoria().addAll(itensDTO);
        return dto;
    }

    public void  converterItensLeilao(Leilao leilao, LeilaoDTO leilaoDTO){
        if (leilaoDTO.getMercadoria().get(0) instanceof ImovelDTO) {
            for (ItemLeilaoDTO item : leilaoDTO.getMercadoria()) {
                // getReferenceById é mais performático aqui: não faz SELECT,
                // apenas cria um proxy com o ID para salvar na tabela de ligação
                Imovel imovel = mapper.map(item,Imovel.class);
                leilao.getItens().add(imovel);
            }
        } else if (leilaoDTO.getMercadoria().get(0).equals(VeiculoDTO.class)) {
            for (ItemLeilaoDTO item : leilaoDTO.getMercadoria()) {
                Veiculo veiculo = mapper.map(item,Veiculo.class);
                leilao.getItens().add(veiculo);
            }
        }
    }
}
