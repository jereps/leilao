package com.youtan.leilao.service;

import com.youtan.leilao.DTO.FinanceiraDTO;
import com.youtan.leilao.model.Financeira;
import com.youtan.leilao.repository.FinanceiraRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceiraServiceImpl implements FinanceiraService {

    private final FinanceiraRepository repositoryFinanceira;
    private final ModelMapper mapper;


    @Override
    public List<FinanceiraDTO> findAll() {
        return repositoryFinanceira.findAll().stream()
                .map(financeira -> mapper.map(financeira, FinanceiraDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FinanceiraDTO getFinanceira(Long id) {
        return repositoryFinanceira.findById(id)
                .map(financeira -> mapper.map(financeira,FinanceiraDTO.class))
                .orElseThrow(() -> new NoSuchElementException(" Instituições Financeira não encontrado"));
    }

    @Override
    @Transactional
    public FinanceiraDTO createFinanceira(FinanceiraDTO financeiraDTO) {
        Financeira financeira = mapper.map(financeiraDTO,Financeira.class);
        repositoryFinanceira.save(financeira);

        return mapper.map(financeira,FinanceiraDTO.class);
    }

    @Override
    @Transactional
    public FinanceiraDTO updateFinanceira(Long id, FinanceiraDTO financeiraDTO) {
        repositoryFinanceira.findById(id)
                .orElseThrow(() -> new NoSuchElementException(" Instituições Financeira não encontrado."));
        Financeira financeira = mapper.map(financeiraDTO,Financeira.class);
        repositoryFinanceira.save(financeira);

        return mapper.map(financeira,FinanceiraDTO.class);
    }

    @Override
    public void deleteFinanceira(Long id) {
        repositoryFinanceira.findById(id)
                .orElseThrow(() -> new NoSuchElementException(" Instituições Financeira não encontrado."));
        repositoryFinanceira.deleteById(id);
    }
}
