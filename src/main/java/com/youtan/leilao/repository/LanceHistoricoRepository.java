package com.youtan.leilao.repository;

import com.youtan.leilao.model.LanceHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanceHistoricoRepository extends JpaRepository<LanceHistorico,Long> {
    List<LanceHistorico> findByItem(Object item);
}
