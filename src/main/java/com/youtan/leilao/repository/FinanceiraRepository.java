package com.youtan.leilao.repository;

import com.youtan.leilao.model.Financeira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceiraRepository extends JpaRepository<Financeira, Long> {
}
