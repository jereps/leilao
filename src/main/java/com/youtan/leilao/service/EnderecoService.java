package com.youtan.leilao.service;

import com.youtan.leilao.DTO.EnderecoDTO;
import com.youtan.leilao.model.Bairro;
import com.youtan.leilao.model.Cidade;
import com.youtan.leilao.model.Endereco;
import com.youtan.leilao.model.Estado;

public interface EnderecoService {

    public Endereco validarEndereco(EnderecoDTO enderecoDTO);
    public Bairro validarBairro(Bairro bairro);
    public Cidade validarCidade(Cidade cidade);
    public Estado validaEstado(Estado estado);
}
