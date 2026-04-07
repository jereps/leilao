package com.youtan.leilao.service;

import com.youtan.leilao.DTO.EnderecoDTO;
import com.youtan.leilao.model.Endereco;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoService {

    public Endereco validarEndereco(Endereco enderecoDTO);
}
