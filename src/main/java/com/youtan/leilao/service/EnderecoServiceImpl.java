package com.youtan.leilao.service;

import com.youtan.leilao.DTO.EnderecoDTO;
import com.youtan.leilao.model.*;
import com.youtan.leilao.repository.*;
import lombok.Data;

@Data
public class EnderecoServiceImpl implements EnderecoService{

    private EnderecoRepository enderecoRepository;
    private CEPRepository cepRepository;
    private BairroRepository bairroRepository;
    private CidadeRepository cidadeRepository;
    private EstadoRepository estadoRepository;


    @Override
    public Endereco validarEndereco(EnderecoDTO enderecoDTO) {
        CEP cep = validarCEP(enderecoDTO.cep());
        Bairro bairro = validarBairro(enderecoDTO.bairro());
        Cidade cidade = validarCidade(enderecoDTO.cidade());
        Estado estado = validaEstado(enderecoDTO.estado());

        Endereco endereco = enderecoRepository.findById(enderecoDTO.id())
                .orElseGet(() -> {
                    Endereco novo = new Endereco();
                    novo.setBairro(bairro);
                    novo.setCidade(cidade);
                    novo.setEstado(estado);
                    novo.setCep(cep);
                    novo.setRua(enderecoDTO.rua());
                    novo.setNumero(enderecoDTO.numero());
                    novo.setComplemento(enderecoDTO.complemento());
                    return enderecoRepository.save(novo);
                });
        return endereco;
    }

    public CEP validarCEP(CEP cep){
        CEP cep1 = cepRepository.findById(cep.getId())
                .orElseGet(() -> {
                    CEP novo = new CEP();
                    novo.setCep(cep.getCep());
                    return cepRepository.save(novo);
                });
        return  cep1;
    }

    public Bairro validarBairro(Bairro bairro){
        Bairro bairro1 = bairroRepository.findById(bairro.getId())
                .orElseGet(() -> {
                    Bairro novo = new Bairro();
                    novo.setNomeBairro(bairro.getNomeBairro());
                    return bairroRepository.save(novo);
                });
        return  bairro1;
    }

    public Cidade validarCidade(Cidade cidade){
        Cidade cidade1 = cidadeRepository.findById(cidade.getId())
                .orElseGet(() -> {
                    Cidade novo = new Cidade();
                    novo.setNome(cidade.getNome());
                    return cidadeRepository.save(novo);
                });
        return  cidade1;
    }

    public Estado  validaEstado(Estado estado){
        Estado estado1 = estadoRepository.findById(estado.getId())
                .orElseGet(() -> {
                    Estado novo = new Estado();
                    novo.setSigla(estado.getSigla());
                    novo.setNome(estado.getNome());
                    return estadoRepository.save(novo);
                });
        return  estado1;
    }
}
