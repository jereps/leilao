package com.youtan.leilao.controller;

import com.youtan.leilao.DTO.FinanceiraDTO;
import com.youtan.leilao.service.FinanceiraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financeira")
public class FinanceiraController {

    private final FinanceiraService financeiraService;

    public FinanceiraController(FinanceiraService financeiraService) {
        this.financeiraService = financeiraService;
    }

    @GetMapping
    public ResponseEntity<List<FinanceiraDTO>> listImoveis() {

        return ResponseEntity.status(HttpStatus.OK).body(financeiraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceiraDTO> getFinanceira(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(financeiraService.getFinanceira(id));
    }

    @PostMapping
    public ResponseEntity<FinanceiraDTO> createFinanceira(@RequestBody FinanceiraDTO financeiraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeiraService.createFinanceira(financeiraDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceiraDTO> updateFinanceira(@PathVariable Long id,@RequestBody FinanceiraDTO financeiraDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(financeiraService.updateFinanceira(id, financeiraDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFinanceira(@PathVariable Long id) {
        financeiraService.deleteFinanceira(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
