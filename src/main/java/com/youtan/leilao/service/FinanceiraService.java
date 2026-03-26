package com.youtan.leilao.service;

import com.youtan.leilao.DTO.FinanceiraDTO;

import java.util.List;

public interface FinanceiraService {

    List<FinanceiraDTO> findAll();

    FinanceiraDTO getFinanceira(Long id);

    FinanceiraDTO createFinanceira(FinanceiraDTO financeiraDTO);

    FinanceiraDTO updateFinanceira(Long id, FinanceiraDTO financeiraDTO);

    void deleteFinanceira(Long id);
    
}
