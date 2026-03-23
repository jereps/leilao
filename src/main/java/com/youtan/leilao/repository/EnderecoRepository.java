package com.youtan.leilao.repository;

import com.youtan.leilao.model.Bairro;
import com.youtan.leilao.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}
