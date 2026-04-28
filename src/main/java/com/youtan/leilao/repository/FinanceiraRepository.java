package com.youtan.leilao.repository;

import com.youtan.leilao.model.Financeira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinanceiraRepository extends JpaRepository<Financeira, Long> {

    Optional<Financeira> findByCnpj(String cnpj);

    /**
     * TODO: Implementar findByCnpjContaining(String cnpj)
     * para suportar a funcionalidade de autocomplete na interface de usuário.
     */
}
