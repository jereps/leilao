package com.youtan.leilao.service;

import com.youtan.leilao.model.*;
import com.youtan.leilao.repository.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class EnderecoServiceImpl implements EnderecoService{

    private final EnderecoRepository enderecoRepository;
    private final CEPRepository cepRepository;
    private final BairroRepository bairroRepository;
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;


    @Override
    public Endereco validarEndereco(Endereco enderecoDTO) {
        CEP cep = validarCEP(enderecoDTO.getCep());
        Bairro bairro = validarBairro(enderecoDTO.getBairro());
        Cidade cidade = validarCidade(enderecoDTO.getCidade());
        Estado estado = validaEstado(enderecoDTO.getEstado());

        Endereco endereco = new Endereco();
        if ( Optional.ofNullable(enderecoDTO.getId()).isPresent()){
            endereco = enderecoRepository.findById(enderecoDTO.getId())
                    .orElseGet(() -> {
                        Endereco novo = new Endereco();
                        novo.setBairro(bairro);
                        novo.setCidade(cidade);
                        novo.setEstado(estado);
                        novo.setCep(cep);
                        novo.setRua(enderecoDTO.getRua());
                        novo.setNumero(enderecoDTO.getNumero());
                        novo.setComplemento(enderecoDTO.getComplemento());
                        return enderecoRepository.save(novo);
                    });
        } else {
            Endereco novo = new Endereco();
            novo.setBairro(bairro);
            novo.setCidade(cidade);
            novo.setEstado(estado);
            novo.setCep(cep);
            novo.setRua(enderecoDTO.getRua());
            novo.setNumero(enderecoDTO.getNumero());
            novo.setComplemento(enderecoDTO.getComplemento());
            return enderecoRepository.save(novo);
        }

        return endereco;
    }

    private CEP validarCEP(CEP cep){
        CEP cep1 = new CEP();
        if ( Optional.ofNullable(cep.getId()).isPresent()){
                    cep1 = cepRepository.findById(cep.getId())
                            .orElseGet(() -> {
                                CEP novo = new CEP();
                                novo.setCep(cep.getCep());
                                return cepRepository.save(novo);
                            });
        } else {
            CEP novo = new CEP();
            novo.setCep(cep.getCep());
            return cepRepository.save(novo);
        }
        return  cep1;
    }

    private Bairro validarBairro(Bairro bairro){
        Bairro bairro1 = new Bairro();
        if ( Optional.ofNullable(bairro.getId()).isPresent()){
            bairro1 = bairroRepository.findById(bairro.getId())
                    .orElseGet(() -> {
                        Bairro novo = new Bairro();
                        novo.setNomeBairro(bairro.getNomeBairro());
                        return bairroRepository.save(novo);
                    });
        } else {
            Bairro novo = new Bairro();
            novo.setNomeBairro(bairro.getNomeBairro());
            return bairroRepository.save(novo);
        }
        return  bairro1;
    }

    private Cidade validarCidade(Cidade cidade){
        Cidade cidade1 = new Cidade();
        if ( Optional.ofNullable(cidade.getId()).isPresent()){
            cidade1 = cidadeRepository.findById(cidade.getId())
                    .orElseGet(() -> {
                        Cidade novo = new Cidade();
                        novo.setNome(cidade.getNome());
                        return cidadeRepository.save(novo);
                    });
        } else {
            Cidade novo = new Cidade();
            novo.setNome(cidade.getNome());
            return cidadeRepository.save(novo);
        }

        return  cidade1;
    }

    private Estado  validaEstado(Estado estado){
        Estado estado1 = new Estado();
        if ( Optional.ofNullable(estado.getId()).isPresent()){
            estado1 = estadoRepository.findById(estado.getId())
                    .orElseGet(() -> {
                        Estado novo = new Estado();
                        novo.setSigla(estado.getSigla());
                        novo.setNome(estado.getNome());
                        return estadoRepository.save(novo);
                    });
        } else {
            Estado novo = new Estado();
            novo.setSigla(estado.getSigla());
            novo.setNome(estado.getNome());
            return estadoRepository.save(novo);
        }

        return  estado1;
    }
}
