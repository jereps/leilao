package com.youtan.leilao.service;

import com.youtan.leilao.DTO.*;
import com.youtan.leilao.model.*;
import com.youtan.leilao.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Service
public class LeilaoServiceImpl implements LeilaoService {

    private final LeilaoRepository leilaoRepository;
    private final ImovelRepository imovelRepository;
    private final VeiculoRepository veiculoRepository;
    private final EnderecoService enderecoService;
    private final LanceHistoricoRepository lanceHistoricoRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final VeiculoService veiculoService;
    private final ImovelService imovelService;


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

        if (!(leilaoDTO.getMercadoria() == null) && !leilaoDTO.getMercadoria().isEmpty()) {
            validarTipoUnico(leilao,leilaoDTO.getMercadoria());
            converterItensLeilao(leilao,leilaoDTO);
        }

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
        if (leilaoDTO.getMercadoria() != null && !leilaoDTO.getMercadoria().isEmpty()) {
            validarTipoUnico(leilao,leilaoDTO.getMercadoria());
            leilao.getItens().addAll(leilaoDTO.getMercadoria());
        }

        leilaoRepository.save(leilao);

        return converterParaDTO(leilao);
    }

    @Override
    @Transactional
    public void deleteLeilao(Long id) {
        leilaoRepository.findById(id)
                .map(leilao -> {
                if ( leilao.getItens() != null && !leilao.getItens().isEmpty()) {
                    List<Object> itens = leilao.getItens();
                    itens.stream().forEach(iten -> deleteItens(iten));
                }
                return true;
            })
        .orElseThrow(() -> new EntityNotFoundException(" Leilão não encontrado."));
        leilaoRepository.deleteById(id);
    }

    private void deleteItens(Object iten) {
        switch (iten) {
            case Imovel i  -> imovelService.deleteImovel(i.getId());
            case Veiculo v -> veiculoService.deleteVeiculo(v.getId());
            default        -> throw new IllegalStateException("Tipo não suportado");
        };
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
        if (novosItens == null || novosItens.isEmpty()  ) return;


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
                leilao.getItens().add(imovelRepository.save(imovel));
            }
        } else if (leilaoDTO.getMercadoria().get(0) instanceof VeiculoDTO) {
            for (ItemLeilaoDTO item : leilaoDTO.getMercadoria()) {
                Veiculo veiculo = mapper.map(item,Veiculo.class);
                leilao.getItens().add(veiculoRepository.save(veiculo));
            }
        }
    }

    @Override
    public void novoLance(LanceDTO lance) {

        validarIncrementoLance(lance.valor());

        Leilao leilao = leilaoRepository.findById(lance.idLeilao())
                .orElseThrow(() -> new EntityNotFoundException("Leilão não encontrado"));

        var item = switch (leilao.getCategoria()){
            case TipoCategoria.IMOVEL -> imovelRepository.findById(lance.idItem());
            case TipoCategoria.VEICULO -> veiculoRepository.findById(lance.idItem());
        };


        if(leilao.getItens().contains(item.get())){
            incrementarValorBem(lance.valor(),item.get());
            historicoLance(lance,item.get());
        }

    }

    private void validarIncrementoLance(BigDecimal valor) {
        String message = """ 
                    Valor para lance não aceito,
                    necessário incremento de no mínimo R$ 200,00
                    """;

        if(valor.remainder(BigDecimal.valueOf(200)).compareTo(BigDecimal.ZERO) > 0){
            throw new IllegalArgumentException(message);
        }
    }


    private void incrementarValorBem(BigDecimal valor, Object o) {
        switch (o){
            case Imovel i -> {
                i.incrementarValor(valor);
                imovelRepository.save(i);
            }
            case Veiculo v -> {
                v.incrementarValor(valor);
                veiculoRepository.save(v);
            }
            default -> throw new IllegalStateException("Tipo não suportado " + o);
        };
    }

    private void historicoLance(LanceDTO lance, Object o) {

        User user = userRepository.findById(lance.idCliente()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Leilao leilao = leilaoRepository.findById(lance.idLeilao()).orElseThrow(() -> new EntityNotFoundException("Leilão não encontrado"));

        LanceHistorico historico = new LanceHistorico();
        historico.setItem(o);
        historico.setValor(lance.valor());
        historico.setUser(user);
        historico.setLeilao(leilao);
        lanceHistoricoRepository.save(historico);
    }

    @Override
    public List<LanceHistoricoDTO> getHistorico(Long id, String tipo) {

        Object item = switch (tipo.toUpperCase()) {
            case "IMOVEL"  -> imovelRepository.getReferenceById(id);
            case "VEICULO" -> veiculoRepository.getReferenceById(id);
            default -> throw new IllegalArgumentException("Tipo inválido");
        };

        return lanceHistoricoRepository.findByItem(item).stream()
                .map(historico -> converterLanceHistoricoParaDTO(historico))
                .collect(Collectors.toList());
    }

    private LanceHistoricoDTO converterLanceHistoricoParaDTO(LanceHistorico historico) {
        ItemLeilaoDTO item = switch (historico.getItem()) {
                    case Imovel i  -> mapper.map(i, ImovelDTO.class);
                    case Veiculo v -> mapper.map(v, VeiculoDTO.class);
                    default        -> throw new IllegalStateException("Tipo não suportado");
                };
        LanceHistoricoDTO lanceHist = mapper.map(historico,LanceHistoricoDTO.class);
        lanceHist.setItem(item);
        return lanceHist;
    }

}
