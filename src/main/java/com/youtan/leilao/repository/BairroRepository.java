package com.youtan.leilao.repository;

import com.youtan.leilao.model.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<Bairro,Long> {
}
